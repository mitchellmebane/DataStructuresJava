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
    private ListNode<T> tail;
    private int size;

    ListNode<T> getHead() {
        if (this.head == null) {
            throw new NoSuchElementException();
        }
        return this.head;
    }
    ListNode<T> getTail() {
        if (this.tail == null) {
            throw new NoSuchElementException();
        }
        return this.tail;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(final T val) {
        final var newNode = new ListNode<>(val);
        this.addNode(newNode);
    }

    void addNode(final ListNode<T> node) {
        if (node.next != null) {
            throw new IllegalArgumentException("addNode can only add single nodes");
        }

        if (this.head == null) {
            this.head = node;
        }
        else {
            this.tail.next = node;
        }
        this.tail = node;

        ++this.size;
    }

    void addNode(final ListNode<T> node, final int size) {
        if (this.head == null) {
            this.head = node;
        }
        else {
            this.tail.next = node;
        }
        this.tail = node;

        this.size += size;
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

        int numEls = 0;
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
            ++numEls;
        }

        if (newHead != null) {
            changed = true;
            this.addNode(newHead, numEls);
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
            this.next = next.next;
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
