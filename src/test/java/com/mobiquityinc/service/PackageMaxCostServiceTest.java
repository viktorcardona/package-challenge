package com.mobiquityinc.service;

import com.mobiquityinc.exception.APIException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PackageMaxCostServiceTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private PackageMaxCostService packageMaxCostService = new PackageMaxCostServiceImpl();

    @Test()
    public void solveTestCaseNotSolution() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "8 : (1,15.3,€34)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            String result = packageMaxCostService.solve(filePath);
            Assert.assertEquals("-", result);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

}