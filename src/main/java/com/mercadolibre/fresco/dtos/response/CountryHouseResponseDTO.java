package com.mercadolibre.fresco.dtos.response;

import com.mercadolibre.fresco.dtos.CountryHouseDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class CountryHouseResponseDTO {
    private String message;
    private CountryHouseDTO countryHouse;
}
