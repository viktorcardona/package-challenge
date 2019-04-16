package com.mobiquityinc.converter;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileConverterTest {

    private FileConverter fileConverter = new FileConverterImpl();

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = APIException.class)
    public void convertWrongFilePath() throws APIException, IOException {
        String filePath = "wrong file path";
        fileConverter.convert(filePath);
    }

    @Test
    public void convertFile2TesCaseValid01() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            FileUtils.writeStringToFile(tempFile, "101 : (1,90.72,€13)", StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            List<TestCase> testCases = fileConverter.convert(filePath);
            Assert.assertEquals(1, testCases.size());
            Assert.assertEquals(new BigDecimal("101"), testCases.get(0).getLimitWeight());
            Assert.assertEquals(1, testCases.get(0).getItems().size());
            Assert.assertEquals(1, testCases.get(0).getItems().get(0).getIndex());
            Assert.assertEquals(new BigDecimal("90.72"), testCases.get(0).getItems().get(0).getWeight());
            Assert.assertEquals(new BigDecimal("13"), testCases.get(0).getItems().get(0).getCost());
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

    @Test
    public void convertFile2TesCaseValid02() throws APIException, IOException {
        File tempFile = null;
        try {
            tempFile = tempFolder.newFile("tempFile.txt");
            StringBuilder content = new StringBuilder();
            content.append("1 : (1,90.72,€13)");
            content.append(System.lineSeparator());
            content.append("2 : (1,90.72,€13)");
            FileUtils.writeStringToFile(tempFile, content.toString(), StandardCharsets.UTF_8);
            String filePath = tempFile.getAbsolutePath();
            List<TestCase> testCases = fileConverter.convert(filePath);
            Assert.assertEquals(2, testCases.size());
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }

}