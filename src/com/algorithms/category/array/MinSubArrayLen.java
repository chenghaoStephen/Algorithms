package com.algorithms.category.array;

/**
 * 给定一个整型数组和一个数字s，找到数组中最短的一个连续子数组，使得连续子数组的数字和sum>=s，返回这个最短的连续子数组的长度
 */
public class MinSubArrayLen {

    public static void main(String[] args) {
        int[] numbers = new int[]{2,3,1,2,4,3};
        int s = 7;
        System.out.println(minSubArrayLen(numbers, s));

    }

    /**
     * 使用两个指针i和j，分别记录滑动窗口的开始和结束位置
     * 如果numbers[i]+...+numbers[j]的值小于s，则右边界j+1；
     * 如果numbers[i]+...+numbers[j]的值大于s，则左边界i+1
     */
    private static int minSubArrayLen(int[] numbers, int s) {
        int l = 0, r = -1; // numbers[l...r]为滑动窗口
        int sum = 0; // 记录[l,r]之间元素的和
        int res = numbers.length + 1; // 最大长度
        while (l < numbers.length) {
            if (r+1 < numbers.length && sum < s) {
                sum += numbers[++r];
            } else {
                sum -= numbers[l++];
            }
            if (sum >= s) {
                res = Math.min(res, r - l + 1);
            }
        }
        if (res == numbers.length + 1) {
            return 0;
        }
        return res;
    }

}
