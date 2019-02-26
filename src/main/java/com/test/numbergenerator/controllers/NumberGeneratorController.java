package com.test.numbergenerator.controllers;

import com.test.numbergenerator.dto.CarNumberDTO;
import com.test.numbergenerator.models.CarNumber;
import com.test.numbergenerator.services.NumberGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/number")
@AllArgsConstructor
public class NumberGeneratorController {
    private NumberGeneratorService numberGeneratorService;

    @RequestMapping("/random")
    public String randomNumber () {
        CarNumberDTO carNumberDTO = numberGeneratorService.getRandomNumber();
        return carNumberDTO.toString();
    }

    @RequestMapping("/next")
    public String nextNumber () {
        CarNumberDTO carNumberDTO = numberGeneratorService.nextNumber();
        return carNumberDTO.toString();
    }
}
