package com.andersen.vfreiman.lessons._1_algorithms_and_data_structures;

public class QueueWithPriority2 {
    private int max_size = 12;
    private int size = 0;
    private Object[] H;
    private final MyComparator comparator;

    public QueueWithPriority2() {
        this(10, null);
    }

    public QueueWithPriority2(MyComparator comparator) {
        this(10, comparator);
    }

    public QueueWithPriority2(int max_size, MyComparator comparator) {
        this.max_size = max_size;
        this.H = new Object[max_size];
        this.comparator = comparator;
    }

    private int parent(int i) {
        return Math.floorDiv(i - 1, 2);
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void siftUp(int i) {
        if (comparator != null)
            siftUpWithComparator(i);
        else
            siftUpWithComparable(i);
    }

    private void siftUpWithComparable(int i) {
        while (i > 0 && ((Comparable)H[parent(i)]).compareTo(H[i]) < 0) {
            Object p_parentI = H[parent(i)];
            H[parent(i)] = H[i];
            H[i] = p_parentI;
            i = parent(i);
        }
    }

    private void siftUpWithComparator(int i) {
        while (i > 0 && comparator.compare(H[parent(i)], H[i]) < 0) {
            Object p_parentI = H[parent(i)];
            H[parent(i)] = H[i];
            H[i] = p_parentI;
            i = parent(i);
        }
    }

    private void siftDown(int i) {
        if (comparator != null)
            siftDownWithComparator(i);
        else
            siftDownWithComparable(i);
    }

    public void siftDownWithComparator(int i) {
        int max_index = i;
        int l = leftChild(i);
        if (l <= size - 1 && comparator.compare(H[l], H[max_index]) > 0) {
            max_index = l;
        }
        int r = rightChild(i);
        if (r <= size - 1 && comparator.compare(H[r], H[max_index]) > 0) {
            max_index = r;
        }
        if (i != max_index) {
            Object p = H[i];
            H[i] = H[max_index];
            H[max_index] = p;
            siftDown(max_index);
        }
    }

    public void siftDownWithComparable(int i) {
        int max_index = i;
        int l = leftChild(i);
        if (l <= size - 1 && ((Comparable) H[l]).compareTo(H[max_index]) > 0) {
            max_index = l;
        }
        int r = rightChild(i);
        if (r <= size - 1 && ((Comparable)H[r]).compareTo(H[max_index]) > 0) {
            max_index = r;
        }
        if (i != max_index) {
            Object p = H[i];
            H[i] = H[max_index];
            H[max_index] = p;
            siftDown(max_index);
        }
    }

    public void insert(Object p) {
        if (isFull()) {
            grow();
        }
        size += 1;
        H[size - 1] = p;
        siftUp(size - 1);
    }

    public Object extractMax() {
        if (isEmpty()) throw new NullPointerException();
        Object result = H[0];
        H[0] = H[size - 1];
        size -= 1;
        siftDown(0);
        return result;
    }

    public Object peek() {
        return H[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == max_size;
    }

    private void grow() {
        int new_max_size = 2 * max_size + 1;
        Object[] newH = new Object[new_max_size];
        for (int i = 0; i < size; i++) {
            newH[i] = H[i];
        }
        H = newH;
        max_size = new_max_size;
    }

    @FunctionalInterface
    public interface MyComparator {
        int compare(Object o1, Object o2);
    }
}