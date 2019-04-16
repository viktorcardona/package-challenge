package com.mobiquityinc.validation;

import com.mobiquityinc.model.TestCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestCaseValidatorImpl implements TestCaseValidator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final int ITEMS_COUNT_LIMIT = 15;

    @Override
    public List<String> extractErrors(List<TestCase> testCases) {
        Set<String> errors = new HashSet<>();
        testCases.forEach(testCase -> errors.addAll(extractErrors(testCase)));
        return new ArrayList<>(errors);
    }

    private Set<String> extractErrors(TestCase testCase) {
        Set<String> errors = new HashSet<>();
        if (isFirstValueGreater(testCase.getLimitWeight(), ONE_HUNDRED)) {
            errors.add("Max weight that a package can take is ≤ 100");
        }
        if (isFirstValueGreater(BigDecimal.ZERO, testCase.getLimitWeight())) {
            errors.add("The weight that a package can take should not be < 0");
        }
        if (testCase.getItems().size() > ITEMS_COUNT_LIMIT) {
            errors.add(String.format("There might be up to %s items. Items found %s", ITEMS_COUNT_LIMIT, testCase.getItems().size()));
        }
        testCase.getItems().forEach(item -> {
            if (isFirstValueGreater(BigDecimal.ZERO, item.getWeight())) {
                errors.add("The weight of an item should not be < 0");
            }
            if (isFirstValueGreater(item.getWeight(), ONE_HUNDRED)) {
                errors.add("Max weight of an item is ≤ 100");
            }
            if (isFirstValueGreater(BigDecimal.ZERO, item.getCost())) {
                errors.add("The cost of an item should not be < 0");
            }
            if (isFirstValueGreater(item.getCost(), ONE_HUNDRED)) {
                errors.add("Max cost of an item is ≤ 100");
            }
        });
        return errors;
    }

    private boolean isFirstValueGreater(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) == 1;
    }

}
