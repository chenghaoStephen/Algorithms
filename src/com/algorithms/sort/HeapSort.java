package com.algorithms.sort;

import com.algorithms.utils.ArrayUtils;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {

        int[] array = {5,9,3,6,1,7,2,8,4};
        // 构建最大堆，从最后一个非叶子节点开始，对所有非叶子结点做下沉调整
        for (int i = array.length/2 - 1; i >= 0; i--) {
            downAdjust(array, i, array.length);
        }
        System.out.println(Arrays.toString(array));

        // 循环将堆顶元素（最大值）移动到集合尾部，调整堆产生新的堆顶
        for (int i = array.length - 1; i > 0; i--) {
            ArrayUtils.swap(array, 0, i);
            downAdjust(array, 0, i);
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 下沉调整节点
     * @param array 数组
     * @param parentIndex 要做下沉调整的节点index
     * @param length 数组长度
     */
    public static void downAdjust(int[] array, int parentIndex, int length) {
        // 记录下沉节点的值
        int pivot = array[parentIndex];
        // 计算孩子节点的索引
        int childIndex = parentIndex * 2 + 1;
        while (childIndex < length) {
            // 选取左右孩子节点的最大值
            if (childIndex + 1 < length && array[childIndex + 1] > array[childIndex]) {
                childIndex++;
            }
            // 父节点比子节点大，跳出
            if (pivot > array[childIndex]) {
                break;
            }
            // 直接赋值
            array[parentIndex] = array[childIndex];
            // 继续向下查找
            parentIndex = childIndex;
            childIndex = 2 * parentIndex + 1;
        }
        // 移动下沉节点
        array[parentIndex] = pivot;
    }

}
