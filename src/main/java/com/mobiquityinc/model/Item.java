package com.mobiquityinc.model;

import java.math.BigDecimal;

public class Item {

    private int index;
    private BigDecimal weight;
    private BigDecimal cost;

    public Item(int index, BigDecimal weight, BigDecimal cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Item{" +
                "index=" + index +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }
}
