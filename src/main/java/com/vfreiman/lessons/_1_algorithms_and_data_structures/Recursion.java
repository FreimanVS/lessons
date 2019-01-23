package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class Recursion {
    private static int factorial(int x) {
        if (x == 1)
            return 1;
        else
            return x * factorial(x - 1);
    }

    public static void main(String[] args) {
        System.out.println(factorial(5));
    }
}
