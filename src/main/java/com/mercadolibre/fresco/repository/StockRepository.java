package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "SELECT * FROM fresh_stocks WHERE stock_id = :id", nativeQuery = true)
    Stock findById(@Param("id") String id);
}
