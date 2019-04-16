package com.mobiquityinc.service;

import com.mobiquityinc.exception.APIException;

public interface PackageMaxCostService {

    String solve(String filePath) throws APIException;

}
