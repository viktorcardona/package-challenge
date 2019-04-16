package com.mobiquityinc.validation;

import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestCaseValidatorTest {

    private TestCaseValidator testCaseValidator = new TestCaseValidatorImpl();

    @Test()
    public void extract0ErrorsEmptyTestCases() {
        List<TestCase> testCases = new ArrayList<>();
        List<String> errors = testCaseValidator.extractErrors(testCases);
        Assert.assertEquals(0, errors.size());
    }

    @Test()
    public void extract0ErrorsTestCasesValid() {
        List<TestCase> testCases = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        int index = 1;
        BigDecimal weight = BigDecimal.ONE;
        BigDecimal cost = BigDecimal.TEN;
        items.add(new Item(index, weight, cost));
        BigDecimal weighLimit = BigDecimal.ONE;
        testCases.add(new TestCase(weighLimit, items));
        List<String> errors = testCaseValidator.extractErrors(testCases);
        Assert.assertEquals(0, errors.size());
    }

    @Test()
    public void extract1ErrorsTestCasesInvalidPackageWeight() {
        List<TestCase> testCases = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        int index = 1;
        BigDecimal weight = BigDecimal.ONE;
        BigDecimal cost = BigDecimal.TEN;
        items.add(new Item(index, weight, cost));
        BigDecimal weighLimit = new BigDecimal("101");
        testCases.add(new TestCase(weighLimit, items));
        List<String> errors = testCaseValidator.extractErrors(testCases);
        Assert.assertEquals(1, errors.size());
    }

    @Test()
    public void extract1ErrorsTestCasesInvalidItemWeight() {
        List<TestCase> testCases = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        int index = 1;
        BigDecimal weight = new BigDecimal("500");
        BigDecimal cost = BigDecimal.TEN;
        items.add(new Item(index, weight, cost));
        BigDecimal weighLimit = new BigDecimal("10");
        testCases.add(new TestCase(weighLimit, items));
        List<String> errors = testCaseValidator.extractErrors(testCases);
        Assert.assertEquals(1, errors.size());
    }

    @Test()
    public void extract1ErrorsTestCasesInvalidItemCost() {
        List<TestCase> testCases = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        int index = 1;
        BigDecimal weight = BigDecimal.TEN;
        BigDecimal cost = new BigDecimal("757");
        items.add(new Item(index, weight, cost));
        BigDecimal weighLimit = new BigDecimal("10");
        testCases.add(new TestCase(weighLimit, items));
        List<String> errors = testCaseValidator.extractErrors(testCases);
        Assert.assertEquals(1, errors.size());
    }
}