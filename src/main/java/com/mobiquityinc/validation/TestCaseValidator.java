package com.mobiquityinc.validation;

import com.mobiquityinc.model.TestCase;

import java.util.List;

public interface TestCaseValidator {

    List<String> extractErrors(List<TestCase> testCases);

}
