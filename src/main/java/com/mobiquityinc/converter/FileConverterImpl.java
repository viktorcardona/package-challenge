package com.mobiquityinc.converter;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.TestCase;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileConverterImpl implements FileConverter {

    @Override
    public List<TestCase> convert(String filePath) throws APIException {
        try {
            return Files.readAllLines(new File(filePath).toPath())
                    .stream()
                    .sequential()
                    .map(this::buildTestCase)
                    .collect(Collectors.toList());
        } catch (Exception exc) {
            throw new APIException(String.format("Error parsing the file %s with error %s", filePath, exc.toString()));
        }
    }

    private TestCase buildTestCase(String line) {
        String[] limitAndItems = line.trim().split("\\s*:\\s*");
        BigDecimal weight = new BigDecimal(limitAndItems[0].trim());
        String[] items = limitAndItems[1].split("[ ()]+");
        List<Item> itemList = Arrays.asList(items).stream().sequential()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::buildItem)
                .collect(Collectors.toList());
        return new TestCase(weight, itemList);
    }

    private Item buildItem(String item) {
        String[] itemParts = item.split("[ ,€]+");
        int index = Integer.parseInt(itemParts[0]);
        BigDecimal weight = new BigDecimal(itemParts[1]);
        BigDecimal cost = new BigDecimal(itemParts[2]);
        return new Item(index, weight, cost);
    }

}
