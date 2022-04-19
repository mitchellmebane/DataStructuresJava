package com.mitchellmebane.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collector;

public class LinkedList<T> implements Iterable<T> {
    static class ListNode<T> {
        T val;
        ListNode<T> next;

        ListNode(final T val) {
            this.val = val;
            this.next = null;
        }
    }

    private ListNode<T> head;

    ListNode<T> getHead() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        return this.head;
    }
    ListNode<T> getTail() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        else {
            ListNode<T> tail = this.head;
            while (tail.next != null) {
                tail = tail.next;
            }
            return tail;
        }
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public LinkedList() {
        this.head = null;
    }

    public void add(final T val) {
        this.addNode(new ListNode<>(val));
    }

    public void addNode(final ListNode<T> node) {
        if (this.head == null) {
            this.head = node;
        }
        else {
            this.getTail().next = node;
        }
    }

    public LinkedList<T> combine(final LinkedList<T> other) {
        this.addAll(other);
        // TODO: I think a combiner only works on temporary lists,
        // so it should be faster to just take the nodes from the
        // other list instead of copying them
        return this;
    }

    boolean addAll(final Iterable<? extends T> c) {
        boolean changed = false;

        ListNode<T> newHead = null, newTail = null;

        for(T el : c) {
            if (newHead == null) {
                newHead = new ListNode<>(el);
                newTail = newHead;
            }
            else {
                final ListNode<T> next = new ListNode<>(el);
                newTail.next = next;
                newTail = next;
            }
        }

        if (newHead != null) {
            changed = true;
            this.addNode(newHead);
        }

        return changed;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(this.head);
    }

    private static class LinkedListIterator<T> implements Iterator<T> {
        ListNode<T> next;

        @Override
        public boolean hasNext() {
            return (next != null);
        }

        @Override
        public T next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            final ListNode<T> next = this.next;
            this.next = this.next.next;
            return next.val;
        }

        LinkedListIterator(final ListNode<T> first) {
            this.next = first;
        }
    }

    public static <T> Collector<T, LinkedList<T>, LinkedList<T>> collector() {
        return Collector.of(LinkedList::new, LinkedList::add, LinkedList::combine);
    }
}
