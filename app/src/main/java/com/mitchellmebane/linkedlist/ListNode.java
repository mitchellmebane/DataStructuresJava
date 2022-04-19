package com.mitchellmebane.linkedlist;

public class ListNode<T> {
    private T val;
    private ListNode<T> next;

    public T getVal() {
        return val;
    }

    public void setVal(final T val) {
        this.val = val;
    }

    public ListNode<T> getNext() {
        return next;
    }

    public void setNext(final ListNode<T> next) {
        this.next = next;
    }

    public ListNode(final T val) {
        this.val = val;
        this.next = null;
    }
}
