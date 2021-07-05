package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.CountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryHouseRepository extends JpaRepository<CountryHouse, Long> {
    CountryHouse findByCountry(String country);
}
