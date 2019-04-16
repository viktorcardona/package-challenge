package com.mobiquityinc.converter;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.TestCase;

import java.util.List;

public interface FileConverter {

    List<TestCase> convert(String filePath) throws APIException;

}
