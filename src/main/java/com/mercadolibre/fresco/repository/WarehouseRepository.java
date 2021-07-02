package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    @Query(value = "SELECT * FROM fresh_warehouses WHERE warehouse_id = :id", nativeQuery = true)
    Warehouse findById(@Param("id") String id);

    Warehouse findByWarehouseCode(String code);
}
