package com.algorithms.sort;

import com.algorithms.utils.ArrayUtils;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {

        int[] array = {5,9,3,6,1,7,2,8,4};
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 分治法递归对[left, right]的元素排序
     * @param array 数组
     * @param left 左边界
     * @param right 有边界
     */
    public static void quickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int idx = singleSort(array, left, right);
        quickSort(array, left, idx - 1);
        quickSort(array, idx + 1, right);
    }

    /**
     * 对[left, right]范围的元素分治，采用单边循环
     * @param array 数组
     * @param left 左边界
     * @param right 右边界
     * @return 基准元素的位置
     */
    public static int singleSort(int[] array, int left, int right) {
        int pivot = array[left];
        int idx = left;
        // 以第一个元素为基准，从第二个元素开始进行比较
        for (int i = left + 1; i <= right; i++) {
            // 小于基准元素则交换
            if (array[i] < pivot) {
                idx++;
                ArrayUtils.swap(array, idx, i);
            }
        }
        // 交换基准元素和最后一个小于它的元素
        ArrayUtils.swap(array, idx, left);
        return idx;
    }



}
