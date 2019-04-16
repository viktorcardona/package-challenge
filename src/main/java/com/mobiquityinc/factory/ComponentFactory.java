package com.mobiquityinc.factory;

import com.mobiquityinc.converter.FileConverter;
import com.mobiquityinc.converter.FileConverterImpl;
import com.mobiquityinc.knapsack.KnapsackAlgorithm;
import com.mobiquityinc.knapsack.KnapsackAlgorithmImpl;
import com.mobiquityinc.service.PackageMaxCostService;
import com.mobiquityinc.service.PackageMaxCostServiceImpl;
import com.mobiquityinc.validation.TestCaseValidator;
import com.mobiquityinc.validation.TestCaseValidatorImpl;

public final class ComponentFactory {

    private ComponentFactory() {}

    public static FileConverter getFileConverter() {
        return new FileConverterImpl();
    }

    public static TestCaseValidator getTestCaseValidator() {
        return new TestCaseValidatorImpl();
    }

    public static PackageMaxCostService getPackageMaxCostService() {
        return new PackageMaxCostServiceImpl();
    }

    public static KnapsackAlgorithm getKnapsackAlgorithm() {
        return new KnapsackAlgorithmImpl();
    }
}
