package com.example.test.sort;

import java.util.Arrays;

public class Sort {

    /**
     * 冒泡排序
     */
    public static int[] maoPaoSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int count = arr.length;
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
        return arr;
    }

    /**
     * 直接插入排序
     */
    public static int[] insertSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int count = arr.length;
        // 左边已排好序数量
        int j;
        // 默认左边第一个是有序，所以从一开始
        for (int i = 1; i < count; i++) {
            j = i;
            int temp = arr[j];
            // 和左边有序 从右往左依次比较，小则继续往前比较
            while (j > 0 && temp < arr[j - 1]) {
                // 往右挪动
                arr[j] = arr[j - 1];
                j--;
            }

            arr[j] = temp;

        }
        return arr;

    }

    /**
     * 归并排序（对两组有序数组进行合并排列）
     */
    private static int[] mergeSort(int[] a, int[] b) {
        int aCount = a.length;
        int bCount = b.length;
        int[] c = new int[aCount + bCount];
        int aNum = 0, bNum = 0, cNum = 0;
        //比较a数组和b数组的元素，谁更小将谁赋值到c数组
        while (aNum < aCount && bNum < bCount) {
            if (a[aNum] < b[bNum]) {
                c[cNum] = a[aNum];
                aNum++;
                cNum++;
            } else {
                c[cNum] = b[bNum];
                bNum++;
                cNum++;
            }
        }
        //如果a数组全部赋值到c数组了，但是b数组还有元素，则将b数组剩余元素按顺序全部复制到c数组
        while (aNum == aCount && bNum < bCount) {
            c[cNum++] = b[bNum++];
        }
        //如果b数组全部赋值到c数组了，但是a数组还有元素，则将a数组剩余元素按顺序全部复制到c数组
        while (bNum == bCount && aNum < aCount) {
            c[cNum++] = a[aNum++];
        }
        return c;
    }

    /**
     * 希尔排序（特殊间隔3h+1，2H，2.2的高级插入排序）
     */
    private static int[] shellKnuthSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int count = arr.length;
        int step = 1;
        int j, temp;
        while (step <= count / 3) {
            step = 3 * step + 1;
        }
        while (step > 0) {
            for (int i = step; i < count; i++) {
                j = i;
                temp = arr[j];
                while (j > step - 1 && temp < arr[j - step]) {
                    arr[j] = arr[j - step];
                    j = j - step;
                }
                arr[j] = temp;
            }
            step = (step - 1) / 3;
        }
        return arr;
    }

    private static int[] shellKnuth2HSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int count = arr.length;
        int j, step = 1, temp;
        while (step <= count / 2) {
            step = step * 2;
        }
        while (step > 0) {
            for (int i = step; i < count; i++) {
                j = i;
                temp = arr[j];
                while (j - step > 0 && temp < arr[j - step]) {
                    arr[j] = arr[j - step];
                    j = j - step;
                }
                arr[j] = temp;
            }
            step = step / 2;
        }
        return arr;
    }

    /**
     * 快速排序
     */
    private static void quickSort(int[] arr, int left, int right) {
        if (right <= left) {
            return;
        } else {
            int pivot = partitionIt(arr, left, right);
            // 对分割后左边的数组进行再次遍历分割
            quickSort(arr, left, pivot - 1);
            // 对分割后右边的数组进行再次遍历分割
            quickSort(arr, pivot + 1, right);
        }
    }

    /**
     * 根据基准值分割数组
     */
    private static int partitionIt(int[] arr, int left, int right) {
        // 左游标 下面从1开始
        int i = left;
        // 右游标
        int j = right + 1;
        // 设置第一个为基准值
        int pivot = arr[left];

        while (true) {
            // 从左往右遍历获取大于基准值的数值的位置
            while (i < right && arr[++i] < pivot) {
            }
            // 从右往左遍历获取小于基准值的数值的位置
            while (j > 0 && arr[--j] > pivot) {
            }
            // 左右游标相遇时候停止， 所以跳出外部while循环
            if (i >= j) {
                break;
            } else {
                // 交换位置
                swap(arr, i, j);
            }
        }
        //基准元素和游标相遇时所指元素交换，为最后一次交换
        swap(arr, left, j);
        // 一趟排序完成， 返回基准元素位置(注意这里基准元素已经交换位置了)
        return j;
    }

    /**
     * 第一个、中间、最后一个三个数取中间的数字
     */
    private static int medianOf3(int[] arr, int left, int right) {
        int center = (left + right) / 3;
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }
        if (arr[center] > arr[right]) {
            swap(arr, center, right);
        }
        if (arr[center] > arr[left]) {
            swap(arr, center, left);
        }
        //array[left]的值已经被换成三数中的中位数， 将其返回
        return arr[left];
    }


    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{5, 7, 30, 9876};
//        for (int i : shellKnuthSort(arr)) {
//            System.out.print(i + ",");
//        }
//        quickSort(arr, 0, arr.length - 1);
//        System.out.print(Arrays.toString(arr));
        int[] brr = new int[]{3, 8, 44, 1234};

        System.out.print(Arrays.toString(mergeSort(arr, brr)));
    }
}
