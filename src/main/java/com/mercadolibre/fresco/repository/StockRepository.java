package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
