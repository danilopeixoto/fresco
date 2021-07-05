package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query(value = "SELECT id FROM warehouses WHERE warehouse_code = :warehouseCode", nativeQuery = true)
    Long getWarehouseIdByCode(@Param("warehouseCode") String warehouseCode);

    @Query(value = "SELECT * FROM warehouses WHERE warehouse_code = :warehouseCode", nativeQuery = true)
    Warehouse findWarehouseByCode(@Param("warehouseCode") String warehouseCode);

}
