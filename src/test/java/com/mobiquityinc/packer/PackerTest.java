package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PackerTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = APIException.class)
    public void packInvalidFilePath() throws APIException {
        Packer.pack("wrong file path");
    }

    @Test()
    public void packTestCaseNotSolution() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "8 : (1,15.3,€34)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            String result = Packer.pack(filePath);
            Assert.assertEquals("-", result);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test()
    public void packTestCaseValid01() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            String result = Packer.pack(filePath);
            Assert.assertEquals("4", result);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test()
    public void packTestCaseValid02() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            String result = Packer.pack(filePath);
            Assert.assertEquals("2,7", result);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test()
    public void packTestCaseValid03() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            String result = Packer.pack(filePath);
            Assert.assertEquals("8,9", result);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test(expected = APIException.class)
    public void packTestCaseInvalidPackageWeightLimit() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "101 : (1,90.72,€13)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            Packer.pack(filePath);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test(expected = APIException.class)
    public void packTestCaseInvalidItemWeight() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "56 : (1,100.01,€13)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            Packer.pack(filePath);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test(expected = APIException.class)
    public void packTestCaseInvalidItemCost() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "56 : (1,40.00,€103)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            Packer.pack(filePath);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test(expected = APIException.class)
    public void packTestCaseInvalidItemEntry() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "56 - (1,40.00,€12)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            Packer.pack(filePath);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

}