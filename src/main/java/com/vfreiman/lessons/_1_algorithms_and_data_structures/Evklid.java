package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class Evklid {
    private static int gcd(int a, int b) {//наибольший общий делитель
        while (a != b) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }
        return a;
    }

    private static int gcd2(int a, int b) {//наибольший общий делитель
        while (a != 0 && b != 0) {
            if (a > b)
                a = a % b;
            else
                b = b % a;
        }
        return a + b;
    }

    public static void main(String[] args) {
        System.out.println(gcd2(30, 18));
    }
}
