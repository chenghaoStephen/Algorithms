package com.algorithms.category.array;

import com.algorithms.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个数组array，将数组中所有0挪到数组的末尾
 */
public class MoveZero {

    public static void main(String[] args) {
        int[] array = {0,1,0,3,5,0,7};
        moveZero3(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 新建一个list，遍历数组，将非0元素push到list中；list赋值给数组，后面补0
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     */
    private static void moveZero1(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                list.add(array[i]);
            }
        }
        int k = 0;
        while (k < list.size()) {
            array[k] = list.get(k);
            k++;
        }
        while (k < array.length) {
            array[k++] = 0;
        }
    }

    /**
     * 使用两个指针，第一个i遍历数组，第二个指针j记录第j个不为0的值
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    private static void moveZero2(int[] array) {
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[j++] = array[i];
            }
        }
        while (j < array.length) {
            array[j++] = 0;
        }
    }

    /**
     * 使用两个指针，第一个i遍历数组，第二个指针j记录第j个不为0的值，如果array[i]不为0，调换i和j
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    private static void moveZero3(int[] array) {
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                if (i != j) {
                    ArrayUtils.swap(array, i, j++);
                } else {
                    j++;
                }
            }
        }
    }

}
