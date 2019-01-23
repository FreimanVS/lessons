package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class BinarySearch {
    private static int binary_search(int[] a, int guessed_number) {
        int from = 0;
        int to = a.length - 1;

        while(from <= to) {
//        int middle = (from + to) / 2; //number overflow is more likely
            int middle = from + ((to - from) / 2);
            int middle_value = a[middle];

            if (guessed_number == middle_value) {
                return middle;
            } else if (guessed_number > middle_value) {
                from = middle + 1;
            } else {
                to = middle - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,3,5,7,9};
        int res1 = binary_search(a, 3); //1
        int res2 = binary_search(a, -100); //none
        System.out.println(res1);
        System.out.println(res2);

    }
}
