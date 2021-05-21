package com.algorithms.appo;

import java.math.BigDecimal;

public class Project {

    /**
     * 分摊金额上限
     */
    private BigDecimal limit;

    /**
     * 有效人月
     */
    private BigDecimal manMon;

    /**
     * 应摊金额
     */
    private BigDecimal shAmount;

    /**
     * 实摊金额
     */
    private BigDecimal reAmount;

    /**
     * 分摊上限/有效人月
     */
    private BigDecimal unitLimit;

    @Override
    public String toString() {
        return reAmount.toString();
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public BigDecimal getManMon() {
        return manMon;
    }

    public void setManMon(BigDecimal manMon) {
        this.manMon = manMon;
    }

    public BigDecimal getShAmount() {
        return shAmount;
    }

    public void setShAmount(BigDecimal shAmount) {
        this.shAmount = shAmount;
    }

    public BigDecimal getReAmount() {
        return reAmount;
    }

    public void setReAmount(BigDecimal reAmount) {
        this.reAmount = reAmount;
    }

    public BigDecimal getUnitLimit() {
        return unitLimit;
    }

    public void setUnitLimit(BigDecimal unitLimit) {
        this.unitLimit = unitLimit;
    }
}
