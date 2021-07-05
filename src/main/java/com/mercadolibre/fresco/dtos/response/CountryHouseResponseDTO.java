package com.mercadolibre.fresco.dtos.response;

import com.mercadolibre.fresco.dtos.CountryHouseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class CountryHouseResponseDTO {
  private String message;
  private CountryHouseDTO countryHouse;
}
