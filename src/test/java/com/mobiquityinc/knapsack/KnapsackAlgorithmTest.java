package com.mobiquityinc.knapsack;

import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class KnapsackAlgorithmTest {

    private KnapsackAlgorithm knapsackAlgorithm = new KnapsackAlgorithmImpl();

    @Test
    public void solve() throws APIException {
        int val[] = {  13,   40,   10,   16,   36,   79,   45,   79,   64 };
        int wt[] = { 9072, 3380, 4315, 3797, 4681, 4877, 8180, 1936,  676 };
        int W = 5600;
        int n = val.length;
        List<Integer> items = knapsackAlgorithm.solve(W, wt, val, n);
        Assert.assertEquals(2, items.size());
        Assert.assertEquals(new Integer(6), items.get(0));
        Assert.assertEquals(new Integer(9), items.get(1));
    }

}