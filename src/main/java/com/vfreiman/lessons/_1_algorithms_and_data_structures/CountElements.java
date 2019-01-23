package com.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.Arrays;

public class CountElements {
    private static int count(int[] a) {
        if (a.length == 0) {
            return 0;
        } else {
            return 1 + count(Arrays.copyOfRange(a, 1, a.length));
        }
    }

    public static void main(String[] args) {
        System.out.println(count(new int[] {1, 2, 3, 10, 20}));
    }
}
