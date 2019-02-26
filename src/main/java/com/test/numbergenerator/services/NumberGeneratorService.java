package com.test.numbergenerator.services;

import com.test.numbergenerator.dto.CarNumberDTO;
import com.test.numbergenerator.exceptions.FullCombinationOfCarNumbersException;
import com.test.numbergenerator.models.CarNumber;
import com.test.numbergenerator.repositories.NumberGeneratorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

@Service
@AllArgsConstructor
public class NumberGeneratorService {
    private NumberGeneratorRepository numberGeneratorRepository;
    public static final ArrayList<Character> serialChars = new ArrayList<>(Arrays.asList('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'));

    public CarNumberDTO getRandomNumber () {
        CarNumberDTO carNumberDTO = generateCarNumber();
        CarNumber carNumber = new CarNumber();
        carNumber.setSerialNumber(codeSerialNumberToInt(carNumberDTO.getSerialNumber()));
        carNumber.setRegistryNumber(carNumberDTO.getRegistryNumber());
        numberGeneratorRepository.save(carNumber);
        return carNumberDTO;
    }

    public CarNumberDTO generateCarNumber() {
        CarNumberDTO carNumberDTO = new CarNumberDTO();
        ArrayList<Integer> serialNumber = getRandomSerialNumberArray();
        Integer registryNumber = new Random().nextInt(999);
        Integer serialNumberCode = codeSerialNumberToInt(serialNumber);
        carNumberDTO.setRegistryNumber(registryNumber);
        carNumberDTO.setSerialNumber(serialNumber);
        if (numberGeneratorRepository.existsBySerialNumberAndRegistryNumber(serialNumberCode,registryNumber)) {
            if (numberGeneratorRepository.count()== (Math.pow(10,3)*Math.pow(12,3))) {
                throw new FullCombinationOfCarNumbersException("All combinations of car numbers already in use");
            }
            carNumberDTO = generateCarNumber();
        }
        return carNumberDTO;
    }

    public CarNumberDTO nextNumber () {
        CarNumberDTO carNumberDTO = new CarNumberDTO();
        CarNumber carNumber = numberGeneratorRepository.findTopByOrderByIdDesc();
        if (carNumber == null) {
            carNumber = new CarNumber();
            carNumber.setRegistryNumber(0);
            carNumber.setSerialNumber(codeSerialNumberToInt(new ArrayList<Integer>(Arrays.asList(0,0,0))));
            numberGeneratorRepository.save(carNumber);
            carNumberDTO.setRegistryNumber(0);
            carNumberDTO.setSerialNumber(new ArrayList<Integer>(Arrays.asList(0,0,0)));
            return carNumberDTO;
        }
        CarNumber newCarNumber = new CarNumber();
        if (carNumber.getRegistryNumber() < 999) {
            Integer newRegistryNumber = carNumber.getRegistryNumber()+1;
            carNumberDTO.setRegistryNumber(newRegistryNumber);
            newCarNumber.setRegistryNumber(newRegistryNumber);
            carNumberDTO.setSerialNumber((decodeSerialNumberToArray(carNumber.getSerialNumber())));
            newCarNumber.setSerialNumber(carNumber.getSerialNumber());
        } else {
            ArrayList<Integer> newSerialNumber = incrementSerialNumber(decodeSerialNumberToArray(carNumber.getSerialNumber()));
            carNumberDTO.setRegistryNumber(0);
            newCarNumber.setRegistryNumber(0);
            carNumberDTO.setSerialNumber(newSerialNumber);
            newCarNumber.setSerialNumber(codeSerialNumberToInt(newSerialNumber));
        }
        if (!numberGeneratorRepository.existsBySerialNumberAndRegistryNumber(newCarNumber.getSerialNumber(),newCarNumber.getRegistryNumber())) {
            numberGeneratorRepository.save(newCarNumber);
        }
        return carNumberDTO;
    }

    public ArrayList<Integer> incrementSerialNumber(ArrayList<Integer> serialNumber) {
        int i = serialNumber.size()-1;
        while (i>=0) {
            int buf = increment(serialNumber.get(i));
            serialNumber.set(i,buf);
            if (buf == 0) {
                i--;
            } else {
                break;
            }
        }
        return serialNumber;
    }
    private int increment(int x) {
        return x == 11 ? 0 : x + 1;
    }

    private ArrayList<Integer>  getRandomSerialNumberArray() {
        ArrayList<Integer> serialNumber = new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            serialNumber.add(new Random().nextInt(serialChars.size()-1));
        }
        return serialNumber;
    }
    public Integer codeSerialNumberToInt (ArrayList<Integer> serialNumber) {
        return (serialNumber.get(0) | (serialNumber.get(1) << 8) | (serialNumber.get(2) << 16));
    }
    public ArrayList<Integer> decodeSerialNumberToArray (Integer serialNumberCode) {
        ArrayList<Integer> decodedSerialNumber = new ArrayList<>();
        Integer firstCharIndex = serialNumberCode & 0xFF;
        Integer secondCharIndex = (serialNumberCode >> 8) & 0xFF;
        Integer thirdCharIndex = (serialNumberCode >> 16) & 0xFF;
        Collections.addAll(decodedSerialNumber, firstCharIndex,secondCharIndex,thirdCharIndex);
        return decodedSerialNumber;
    }
}
