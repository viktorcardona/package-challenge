package com.mobiquityinc.knapsack;

import java.util.List;

public interface KnapsackAlgorithm {

    List<Integer> solve(int packageWeightLimit, int[] weights, int[] costs, int n);

}
