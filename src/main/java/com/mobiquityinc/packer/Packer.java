package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.factory.ComponentFactory;

public class Packer {

    private Packer() {}

    public static String pack(String filePath) throws APIException {
        return ComponentFactory.getPackageMaxCostService().solve(filePath);
    }

}
