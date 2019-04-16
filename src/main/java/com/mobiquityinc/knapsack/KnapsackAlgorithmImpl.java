package com.mobiquityinc.knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnapsackAlgorithmImpl implements KnapsackAlgorithm {

    /**
     *
     * This algorithm is used due to its performance.
     *
     * Knapsack Algorithm implementation.
     * This implementation is based on dynamic programming.
     * The performance is O(numberItems*packageWeightLimit)
     *
     * @param packageWeightLimit
     * @param weights
     * @param costs
     * @param numberItems
     * @return list of items that maximize the cost of items in the package
     */
    @Override
    public List<Integer> solve(int packageWeightLimit, int[] weights, int[] costs, final int numberItems) {
        int[][] matrix = new int[numberItems + 1][packageWeightLimit + 1];
        // Build table matrix[][] in bottom up manner
        for (int indexItem = 0; indexItem <= numberItems; indexItem++) {
            for (int weightDelta = 0; weightDelta <= packageWeightLimit; weightDelta++) {
                if (indexItem == 0 || weightDelta == 0) {
                    matrix[indexItem][weightDelta] = 0;
                } else if (weights[indexItem - 1] <= weightDelta) {
                    int costItemIncluded = costs[indexItem - 1] + matrix[indexItem - 1][weightDelta - weights[indexItem - 1]];
                    int costItemNotIncluded = matrix[indexItem - 1][weightDelta];
                    matrix[indexItem][weightDelta] = Math.max(costItemIncluded, costItemNotIncluded);
                } else {
                    matrix[indexItem][weightDelta] = matrix[indexItem - 1][weightDelta];
                }
            }
        }
        // the maxCost of Knapsack
        int maxCost = matrix[numberItems][packageWeightLimit];
        return extractItemsWithMaxCost(numberItems, maxCost, matrix, costs, weights, packageWeightLimit);
    }

    private List<Integer> extractItemsWithMaxCost(int numberItems, int maxCost, int[][] matrix, int[] costs, int[] weights, int packageWeightLimit) {
        List<Integer> itemIndexes = new ArrayList<>();
        int weightDelta = packageWeightLimit;
        for (int indexItem = numberItems; indexItem > 0 && maxCost > 0; indexItem--) {
            if (maxCost != matrix[indexItem - 1][weightDelta]) {
                //This item is included.
                itemIndexes.add(indexItem);
                //Update cost and weight
                maxCost = maxCost - costs[indexItem - 1];
                weightDelta = weightDelta - weights[indexItem - 1];
            }
        }
        Collections.sort(itemIndexes);
        return itemIndexes;
    }

}
