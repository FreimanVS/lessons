package com.andersen.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.Arrays;
import java.util.List;

public class SummarizeElements {
    private static int sum(int[] a) {
        if (a.length == 0) {
            return 0;
        } else {
            return a[0] + sum(Arrays.copyOfRange(a, 1, a.length));
        }
    }

    public static void main(String[] args) {
        System.out.println(sum(new int[] {1, 2, 3, 5}));
    }
}
