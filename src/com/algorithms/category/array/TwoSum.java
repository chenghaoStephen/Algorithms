package com.algorithms.category.array;

import java.util.Arrays;

/**
 * 给定一个有序整型数组和一个整数target，在其中寻找两个元素，使其和为target
 */
public class TwoSum {

    public static void main(String[] args) {
        int[] numbers = {1,2,3,4,7,9};
        int target = 7;
        System.out.println(Arrays.toString(twoSum(numbers, target)));
    }

    /**
     * 使用对撞指针，使用对撞指针，i从0开始，j从array.length-1开始
     * 如果numbers[i] + numbers[j] > target，j-1；如果numbers[i] + number[j] < target，i+1
     */
    private static int[] twoSum(int[] numbers, int target) {
        // 数组长度 > 2
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            if (numbers[l] + numbers[r] == target) {
                return new int[]{l, r};
            } else if (numbers[l] + numbers[r] < target) {
                l++;
            } else {
                r--;
            }
        }
        return new int[]{};
    }
}
