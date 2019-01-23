package com.vfreiman.lessons._1_algorithms_and_data_structures;

public class QuickWithEquals {
    public static void sort(int a[]) {
        if (a.length > 1) {
            sort(a, 0, a.length - 1);
        }
    }

    private static void sort(int a[], int l, int r) {
        while (l < r) {
            int[] m = partition(a, l, r);
            if (m[0] - l <= r - m[1]) {
                sort(a, l, m[0] - 1);
                l = m[1] + 1;
            } else {
                sort(a, m[1] + 1, r);
                r = m[0] - 1;
            }
        }
    }

    private static int[] partition(int[] a, int l, int r) {
        //a possible median
        int mInd = (l + r) >> 1;
        int medianInd;
        if ((a[l] >= a[r] && a[l] <= a[mInd]) || (a[l] >= a[mInd] && a[l] <= a[r]))
            medianInd = l;
        else if ((a[r] >= a[l] && a[r] <= a[mInd]) || (a[r] >= a[mInd] && a[r] <= a[l]))
            medianInd = r;
        else
            medianInd = mInd;
        swap(a, l, medianInd);

        int x = a[l];
        int k = l;
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (a[i] <= x) {
                k ++;
                swap(a, k, i);
                if (a[k] < x) {
                    j ++;
                    swap(a, j, k);
                }
            }
        }
        swap(a, l, j);
        return new int[] {j, k};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
