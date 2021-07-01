package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.CountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryHouseRepository extends JpaRepository<CountryHouse, Long> {
    CountryHouse findByCountry(String country);
}
