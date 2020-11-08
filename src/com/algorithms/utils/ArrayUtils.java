package com.algorithms.utils;

public class ArrayUtils {

    public static void swap(int[] array, int s, int e) {
        int temp = array[s];
        array[s] = array[e];
        array[e] = temp;
    }

}
