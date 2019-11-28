package com.example.base.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PrintMinArray {

    private static String[] orderArray(int[] array) {

        int count = array.length;
        String[] s = new String[count];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            s[i] = String.valueOf(array[i]);
        }

        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < (count - i - 1); j++) {
                String c1 = s[j] + s[j + 1];
                String c2 = s[j + 1] + s[j];
                if (c1.compareTo(c2) > 0) {
                    exchange(s, j, j + 1);
                }
            }
        }
        return s;
    }

    /**
     * 交换位置
     */
    private static void exchange(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int[] orderSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int count = arr.length;
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count  - i -1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
        return arr;
    }


    public static String printMinArray() {
        int[] temp = new int[]{3, 32, 321, 21, 1, 3, 45};
        int count = temp.length;
        String[] s = new String[count];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            s[i] = String.valueOf(temp[i]);
        }
        Arrays.sort(s, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String c1 = s1 + s2;
                String c2 = s2 + s1;
                return c1.compareTo(c2);
            }
        });

        for (int i = 0; i < s.length; i++) {
            sb.append(s[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] input = new int[]{332, 32, 321};
//        for (String s : orderArray(input)) {
////            System.out.print(s);
//        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : orderSort(input)) {
            stringBuilder.append(i);
        }
        System.out.print(stringBuilder.toString());
//        System.out.print(printMinArray());

    }


}
