package com.andersen.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionSort {
    private static int getIndexOfMinElement(List<Integer> a) {
        int min = a.get(0);
        int min_index = 0;
        for (int i = 1; i < a.size(); i++) {
            if (a.get(i) < min) {
                min = a.get(i);
                min_index = i;
            }
        }
        return min_index;
    }

    private static List<Integer> selectionSort(List<Integer> a) {
        List<Integer> newA = new ArrayList<>(a.size());
        int size = a.size();
        for (int i = 0; i < size; i++) {
            int indexOfMin = getIndexOfMinElement(a);
            int valueOfMin = a.get(indexOfMin);
            newA.add(valueOfMin);
            a.remove(indexOfMin);
        }
        return newA;
    }

    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>(Arrays.asList(5, 3, 6, 2, 10));
        System.out.println(selectionSort(a)); //[2, 3, 5, 6, 10]
    }
}
