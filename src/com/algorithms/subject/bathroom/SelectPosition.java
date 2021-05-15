package com.algorithms.subject.bathroom;

import java.util.PriorityQueue;

/**
 * 有N个淋浴间，第i个人进来，会选择最大的没有人的空间，选择中间的位置
 * 如果中间位置有两个，选择靠左的位置
 * 问题：第k个人进来，选择的位置
 */
public class SelectPosition {

    /**
     * 共有num个位置，第k个人进来，返回他的位置
     * @param num
     * @param k
     * @return
     */
    private static int selectPos(int num, int k) {
        // 维护一个最大堆，元素为区间Section（PriorityQueue默认是最小堆）
        PriorityQueue<Section> queue = new PriorityQueue<>((a, b) -> {
            // 区间长度相同时，优先选择靠左的位置
            return (a.getSec() == b.getSec()) ? (a.getStartIdx() - b.getStartIdx()) : (b.getSec() - a.getSec());
        });
        // 初始化Section
        Section section = new Section(0, num - 1);
        queue.offer(section);
        int pos = 0;
        for (int i = 0; i < k; i++) {
            if (queue.isEmpty()) {
                return -1;
            }
            // 取出堆顶最大区间元素
            Section maxSection = queue.poll();
            // 取出中间位置元素
            pos = maxSection.getStartIdx() + (maxSection.getSec()-1)/2;
            // 如果左右生成新的区间，加入队列
            if (pos - maxSection.getStartIdx() > 0) {
                queue.offer(new Section(maxSection.getStartIdx(), pos - 1));
            }
            if (maxSection.getEndIdx() - pos > 0) {
                queue.offer(new Section(pos + 1, maxSection.getEndIdx()));
            }
        }
        return pos + 1;
    }

    public static void main(String[] args) {
        System.out.println(selectPos(10000000, 2000000));
    }

}

class Section {

    private int startIdx;
    private int endIdx;
    private int sec;

    public Section(int startIdx, int endIdx) {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.sec = this.endIdx - this.startIdx + 1;
    }

    public int getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }

    public int getEndIdx() {
        return endIdx;
    }

    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }
}