package com.test.numbergenerator.dto;

import com.test.numbergenerator.services.NumberGeneratorService;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CarNumberDTO {

    private static final String region = "116 RUS";

    private Integer registryNumber;

    private ArrayList<Integer> serialNumber;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String format = String.format("%%0%dd", 3);
        String formattedRegistryNumber = String.format(format, registryNumber);
        stringBuilder.append(NumberGeneratorService.serialChars.get(serialNumber.get(0))).append(formattedRegistryNumber).append(NumberGeneratorService.serialChars.get(serialNumber.get(1))).append(NumberGeneratorService.serialChars.get(serialNumber.get(2))).append(" ").append(region);
        return stringBuilder.toString();
    }
}
