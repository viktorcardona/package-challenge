package com.mobiquityinc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestCase {

    private BigDecimal limitWeight;
    private List<Item> items;

    public TestCase(BigDecimal weight, List<Item> items) {
        this.limitWeight = weight;
        this.items = items;
    }

    public BigDecimal getLimitWeight() {
        return limitWeight;
    }

    public List<Item> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "limitWeight=" + limitWeight +
                ", items=" + items +
                '}';
    }
}
