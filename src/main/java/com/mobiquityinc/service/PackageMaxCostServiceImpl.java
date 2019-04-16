package com.mobiquityinc.service;

import com.mobiquityinc.converter.FileConverter;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.factory.ComponentFactory;
import com.mobiquityinc.knapsack.KnapsackAlgorithm;
import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.TestCase;
import com.mobiquityinc.validation.TestCaseValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PackageMaxCostServiceImpl implements PackageMaxCostService {

    @Override
    public String solve(String filePath) throws APIException {
        FileConverter fileConverter = ComponentFactory.getFileConverter();
        List<TestCase> testCases = fileConverter.convert(filePath);
        validateTestCases(testCases);
        return testCases.stream().sequential()
                .map(this::solve)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private void validateTestCases(List<TestCase> testCases) throws APIException {
        TestCaseValidator testCaseValidator = ComponentFactory.getTestCaseValidator();
        List<String> errors = testCaseValidator.extractErrors(testCases);
        if (!errors.isEmpty()) {
            throw new APIException(errors.stream().collect(Collectors.joining(System.lineSeparator())));
        }
    }

    private String solve(TestCase testCase) {
        // This decimalParts is the magic part
        // It allows to transform the decimal numbers into integers for being able of using knapsack algorithm
        BigDecimal decimalParts = getNumber2RemoveDecimalPart(testCase);

        // Items are transformed:
        List<Item> items = transformItemsForKnapSack(testCase, decimalParts);

        int n = testCase.getItems().size();
        int[] weights = new int[n];
        int[] costs = new int[n];

        int index = 0;
        for (Item item: items) {
            weights[index] = item.getWeight().intValue();
            costs[index] = item.getCost().intValue();
            index++;
        }

        int packageWeightLimit = testCase.getLimitWeight().multiply(decimalParts).intValue();

        List<Integer> itemIndexes = solveWithKnapSack(packageWeightLimit, weights, costs, n);

        String solution = itemIndexes.stream().sequential()
                .map(ind -> items.get(ind - 1).getIndex())
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return solution.trim().isEmpty() ? "-" : solution;
    }

    private List<Integer> solveWithKnapSack(int packageWeightLimit, int[] weights, int[] costs, int n) {
        KnapsackAlgorithm knapsackAlgorithm = ComponentFactory.getKnapsackAlgorithm();
        return knapsackAlgorithm.solve(packageWeightLimit, weights, costs, n);
    }

    private List<Item> transformItemsForKnapSack(TestCase testCase, BigDecimal decimalParts) {
        List<Item> items = testCase.getItems().stream().sequential()
                .map(i -> removeDecimalPart(i, decimalParts))
                .collect(Collectors.toList());
        Comparator<Item> comparedByCost = Comparator.comparing(Item::getCost);
        Comparator<Item> comparedByWeight = Comparator.comparing(Item::getWeight);
        Comparator<Item> comparatorOfItems = comparedByCost.reversed().thenComparing(comparedByWeight);
        Collections.sort(items, comparatorOfItems);
        return items;
    }

    private Item removeDecimalPart(Item item, BigDecimal decimalParts) {
        item.setCost(item.getCost().multiply(decimalParts));
        item.setWeight(item.getWeight().multiply(decimalParts));
        return item;
    }

    private BigDecimal getNumber2RemoveDecimalPart(TestCase testCase) {
        List<BigDecimal> numbers = new ArrayList<>();
        numbers.add(testCase.getLimitWeight());
        testCase.getItems().forEach(item -> {
            numbers.add(item.getWeight());
            numbers.add(item.getCost());
        });
        int numberOfDecimalParts = numbers.parallelStream().mapToInt(this::getNumberOfDecimalPlaces).max().orElse(0);
        return toBigDecimal(numberOfDecimalParts);
    }

    private int getNumberOfDecimalPlaces(BigDecimal value) {
        String string = value.stripTrailingZeros().toPlainString();
        int index = string.indexOf('.');
        return index < 0 ? 0 : string.length() - index - 1;
    }

    private BigDecimal toBigDecimal(int numberOfDecimalParts) {
        StringBuilder number = new StringBuilder("1");
        for (int i = 0; i < numberOfDecimalParts; i++) {
            number.append("0");
        }
        return new BigDecimal(number.toString());
    }

}
