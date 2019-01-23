package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class MyDequeObjects {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        private int data;
        private Node prev;
        private Node next;

        private Node(int data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public void pushFront(int a) {
        Node newNode = new Node(a, null, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        size ++;
    }

    public int popFront() {
        if (isEmpty()) throw new NullPointerException("The MyDequeObjects is empty!");
        int result = head.data;
        if (head.next != null) {
            head.next.prev = null;
            head = head.next;
        } else {
            head = null;
            tail = null;
        }
        size --;
        return result;
    }

    public void pushBack(int a) {
        Node newNode = new Node(a, null, null);
        if (tail == null) {
            tail = newNode;
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size ++;
    }

    public int popBack() {
        if (isEmpty()) throw new NullPointerException("The MyDequeObjects is empty!");
        int result = tail.data;
        if (tail.prev != null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            tail = null;
            head = null;
        }
        size --;
        return result;
    }

    public int peekFront() {
        if (isEmpty()) throw new NullPointerException("The MyDequeObjects is empty!");
        return head.data;
    }

    public int peekBack() {
        if (isEmpty()) throw new NullPointerException("The MyDequeObjects is empty!");
        return tail.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}
