package com.example.test;

/**
 * 折半查找
 */
public class HalfFind {

    /**
     * 循环
     */
    public static int halfFind(int[] arrs, int value) {
        int count = arrs.length;
        int low = 0;
        int high = count - 1;
        int middle;
        while (low <= high) {
            middle = (low + high) / 2;
            if (value == arrs[middle]) {
                return middle;
            } else if (value > arrs[middle]) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    /**
     * 递归
     */
    public static int halfFindRecursion(int[] arrs, int value, int low, int high) {
        if (low > high) {
            return -1;
        }
        int middle = (low + high) / 2;

        if (value == arrs[middle]) {
            return middle;
        } else if (value > arrs[middle]) {
            return halfFindRecursion(arrs, value, middle + 1, high);
        } else {
            return halfFindRecursion(arrs, value, low, middle - 1);
        }
    }

    public static void main(String[] args) {
        int[] temp = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.print(String.valueOf(halfFindRecursion(temp, 10, 0, 8)));
    }
}
