package com.algorithms.subject.blankcase;

public class Case {

    private int l;
    private int w;
    private int h;

    public Case(int l, int w, int h) {
        this.l = l;
        this.w = w;
        this.h = h;
    }

    /**
     * 判断该箱子是否能被箱子c装下
     */
    public boolean isSmallerThan(Case c) {
        return this.l < c.l && this.w < c.w && this.h < c.h;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

}
