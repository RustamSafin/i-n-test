package com.test.numbergenerator.services;

import com.test.numbergenerator.dto.CarNumberDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class NumberGeneratorServiceTest {

    @Autowired
    private NumberGeneratorService numberGeneratorService;

    @Test
    public void serialNumberShouldBeIncremented() {
        assertEquals(numberGeneratorService.incrementSerialNumber(new ArrayList<Integer>(Arrays.asList(11, 2, 11))), new ArrayList<Integer>(Arrays.asList(11, 3, 0)));
    }

    @Test
    public void SerialNumberShouldBeDecodedToArray() {
        ArrayList<Integer> array = new ArrayList<Integer>(Arrays.asList(11, 2, 11));
        assertEquals(numberGeneratorService.decodeSerialNumberToArray(numberGeneratorService.codeSerialNumberToInt(array)),array);
    }

    @Test
    public void randomCarNumberShouldBeGenerated() {
        CarNumberDTO carNumberDTO = numberGeneratorService.generateCarNumber();
        assertNotNull("registry number should be generated", carNumberDTO.getRegistryNumber());
        assertNotNull("serial number should be generated", carNumberDTO.getSerialNumber());
        assertEquals("serial numbers size must be 3", 3, carNumberDTO.getSerialNumber().size());
    }

}