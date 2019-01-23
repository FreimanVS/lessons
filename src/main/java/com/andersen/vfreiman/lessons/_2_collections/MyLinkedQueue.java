package com.andersen.vfreiman.lessons._2_collections;

import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;

public class MyLinkedQueue<E> extends AbstractQueue<E> implements Queue<E>, Cloneable, Serializable {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        private E data;
        private Node<E> prev;
        private Node<E> next;

        private Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyLinkedQueueIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean offer(E e) {
        Node<E> newNode = new Node<>(e, null, null);

        if (size == 0) {
            tail = newNode;
            head = newNode;
        } else {
            link(newNode, tail);
            tail = newNode;
        }
        size ++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;

        E result = head.data;

        Node<E> prevNode = head.prev;
        unlink(prevNode, head);
        head = prevNode;

        size --;
        return result;
    }

    @Override
    public E peek() {
        return isEmpty() ? null : head.data;
    }

    private void link(Node<E> n1, Node<E>  n2) {
        if (Objects.nonNull(n1)) n1.next = n2;
        if (Objects.nonNull(n2)) n2.prev = n1;
    }

    private void unlink(Node<E> n1, Node<E>  n2) {
        if (Objects.nonNull(n1)) n1.next = null;
        if (Objects.nonNull(n2)) n2.prev = null;
    }

    private class MyLinkedQueueIterator implements Iterator<E> {

        private Node<E> next = head;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            Node<E> cur = next;
            next = next.prev;
            return cur.data;
        }
    }
}
