package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class QueueWithPriority {
    private int max_size;
    private int size = 0;
    private int[] H;

    public QueueWithPriority(int max_size) {
        this.max_size = max_size;
        H = new int[max_size];
    }

    public void insert(int p) {
        if (size == max_size) throw new RuntimeException("The PriorityWithQueue is full!");
        size += 1;
        H[size - 1] = p;
        siftUp(size - 1);
    }

    public int extractMax() {
        if (isEmpty()) throw new NullPointerException("The PriorityWithQueue is empty!");
        int result = H[0];
        H[0] = H[size - 1];
        size -= 1;
        siftDown(0);
        return result;
    }

    public int peek() {
        if (isEmpty()) throw new NullPointerException("The PriorityWithQueue is empty!");
        return H[0];
    }

    public boolean isEmpty() {
        return size == 0;
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
        while (i > 0 && H[parent(i)] < H[i]) {
            int p_parentI = H[parent(i)];
            H[parent(i)] = H[i];
            H[i] = p_parentI;
            i = parent(i);
        }
    }

    private void siftDown(int i) {
        int max_index = i;
        int l = leftChild(i);
        if (l <= size - 1 && H[l] > H[max_index]) {
            max_index = l;
        }
        int r = rightChild(i);
        if (r <= size - 1 && H[r] > H[max_index]) {
            max_index = r;
        }
        if (i != max_index) {
            int p = H[i];
            H[i] = H[max_index];
            H[max_index] = p;
            siftDown(max_index);
        }
    }
}