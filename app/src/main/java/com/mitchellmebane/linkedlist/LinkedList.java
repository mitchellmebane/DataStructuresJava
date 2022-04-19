package com.mitchellmebane.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collector;

public class LinkedList<T> implements Iterable<T> {
    private ListNode<T> head;

    public ListNode<T> getHead() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        return this.head;
    }
    public ListNode<T> getTail() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        else {
            ListNode<T> tail = this.head;
            while (tail.getNext() != null) {
                tail = tail.getNext();
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
            this.getTail().setNext(node);
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
                newTail.setNext(next);
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
            this.next = this.next.getNext();
            return next.getVal();
        }

        LinkedListIterator(final ListNode<T> first) {
            this.next = first;
        }
    }

    public static <T> Collector<T, LinkedList<T>, LinkedList<T>> collector() {
        return Collector.of(LinkedList::new, LinkedList::add, LinkedList::combine);
    }
}
