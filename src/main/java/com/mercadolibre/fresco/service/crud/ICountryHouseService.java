package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.dtos.CountryHouseDTO;

public interface ICountryHouseService extends ICRUD<CountryHouseDTO> {
  CountryHouseDTO findByCountry(String country);
}
