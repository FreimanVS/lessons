package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class MyDeque {
    private int[] buffer;
    private int head;
    private int tail;
    private int size;
    private int capacity;
    private boolean headIsMoved = false;
    private boolean tailIsMoved = false;

    public MyDeque() {
        this(10);
    }

    public MyDeque(int capacity) {
        this.buffer = new int[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
        this.capacity = buffer.length;
    }

    public void pushFront(int a) {
        if (isFull()) {
            grow();
            pushFront(a);
        } else {
            if (!headIsMoved)
                headIsMoved = true;
            head = (capacity + head - 1) % capacity;
            buffer[head] = a;
            size ++;
        }
    }

    public int popFront() {
        if (isEmpty()) throw new NullPointerException("The MyDeque is empty!");
        int result = buffer[head];
        head = (head + 1) % capacity;
        size --;
        return result;
    }

    public void pushBack(int a) {
        if (isFull()) {
            grow();
            pushBack(a);
        } else {
            if (!tailIsMoved)
                tailIsMoved = true;
            buffer[tail] = a;
            tail = (tail + 1) % capacity;
            size ++;
        }
    }

    public int popBack() {
        if (isEmpty()) throw new NullPointerException("The MyDeque is empty!");
        tail = (capacity + tail - 1) % capacity;
        int result = buffer[tail];
        size --;
        return result;
    }

    public int peekFront() {
        if (isEmpty()) throw new NullPointerException("The MyDeque is empty!");
        return buffer[head];
    }

    public int peekBack() {
        if (isEmpty()) throw new NullPointerException("The MyDeque is empty!");
        return buffer[(capacity + tail - 1) % capacity];
    }

    private void grow() {
        int newCap = 2 * capacity + 1;
        int[] newBuffer = new int[newCap];
        if (headIsMoved) {
            int diffCap = newCap - capacity;
            System.arraycopy(buffer, head, newBuffer, head + diffCap, buffer.length - head);
            head += diffCap;
        }
        if (tailIsMoved) {
            tail = (capacity + tail - 1) % capacity;
            System.arraycopy(buffer, 0, newBuffer, 0, tail + 1);
        }
        buffer = newBuffer;
        capacity = newCap;
        if (tailIsMoved)
            tail = (tail + 1) % capacity;
    }

    public void insertionSort() {
        for (int i = 1; i < size; i++) {
            int j = i;
            while (j > 0 && buffer[j] < buffer[j - 1]) {
                int temp = buffer[j];
                buffer[j] = buffer[j - 1];
                buffer[j - 1] = temp;
                j -= 1;
            }
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return capacity == size;
    }

    public int size() {
        return size;
    }

    public String toString() {
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(buffer[(head + i) % capacity]).append(" ");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
