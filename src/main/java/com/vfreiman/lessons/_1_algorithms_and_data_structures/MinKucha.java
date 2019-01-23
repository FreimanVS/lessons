package com.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.ArrayList;
import java.util.List;

public class MinKucha {
    private int size = 0;
    private int[] H;
    private int count = 0;
    private List<Pair> listOfMessages = new ArrayList<>();

    public MinKucha(int max_size) {
        size = max_size;
        H = new int[max_size];
    }

    public int leftChild(int i) {
        return 2 * i + 1;
    }

    public int rightChild(int i) {
        return 2 * i + 2;
    }

    public void siftDown(int i) {
        int min_index = i;
        int l = leftChild(i);
        if (l <= size - 1 && H[l] < H[min_index]) {
            min_index = l;
        }
        int r = rightChild(i);
        if (r <= size - 1 && H[r] < H[min_index]) {
            min_index = r;
        }

        if (i != min_index) {
            count ++;
            listOfMessages.add(new MinKucha.Pair(i, min_index));
            int p = H[i];
            H[i] = H[min_index];
            H[min_index] = p;
            siftDown(min_index);
        }
    }

    public void buildHeap() {
        for (int i = Math.floorDiv(size - 1, 2); i >= 0; i--) {
            siftDown(i);
        }
    }

    public int[] getH() {
        return H;
    }

    public int getCount() {
        return count;
    }

    public List<MinKucha.Pair> getListOfMessages() {
        return listOfMessages;
    }

    static class Pair {
        private int i;
        private int min_index;

        public Pair(int i, int min_index) {
            this.i = i;
            this.min_index = min_index;
        }

        @Override
        public String toString() {
            return i + " " + min_index;
        }
    }
}