package com.andersen.vfreiman.lessons._1_algorithms_and_data_structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MaxElement {
    private static int findMaxElement(int[] a) {
        if (a.length == 1) {
            return a[0];
        } else {
            if (a[1] > a[0]) {
                return findMaxElement(Arrays.copyOfRange(a, 1, a.length));
            } else {
                int[] first = new int[] {a[0]};
                int[] second = new int[0];
                if (a.length >= 3) {
                    second = Arrays.copyOfRange(a, 2, a.length);
                }
                List<Integer> firstList = Arrays.stream(first).boxed().collect(Collectors.toList());
                Arrays.stream(second).boxed().forEach(firstList::add);
                int[] result = firstList.stream().mapToInt(e -> e).toArray();
                return  findMaxElement(result);
            }
        }
    }

    private static int findMaxElement2(int[] a) {
        if (a.length == 2) {
            return a[0] > a[1] ? a[0] : a[1];
        } else {
            int sub_max = findMaxElement2(Arrays.copyOfRange(a, 1, a.length));
            return a[0] > sub_max ? a[0] : sub_max;
        }
    }

    public static void main(String[] args) {
        System.out.println(findMaxElement(new int[] {1, 5, 3, 10, 8}));
        System.out.println(findMaxElement2(new int[] {1, 5, 3, 10, 8}));
    }
}
