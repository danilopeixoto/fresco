package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.dtos.response.aggregation.IWarehouseProductCountDTO;
import com.mercadolibre.fresco.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query(value = "SELECT * FROM warehouses WHERE warehouse_code = :warehouseCode", nativeQuery = true)
    Warehouse findWarehouseByCode(@Param("warehouseCode") String warehouseCode);

    @Query(value =
            "SELECT w.warehouse_code as warehouseCode, SUM(s.cur_quantity) as totalQuantity FROM warehouses w " +
                    "INNER JOIN warehouse_section ws ON ws.warehouse_id = w.id " +
                    "INNER JOIN stocks s ON s.warehouse_section_id = ws.id " +
                    "INNER JOIN products p ON p.id = s.product_id " +
                    "WHERE p.product_code = :productCode GROUP BY w.warehouse_code"
            , nativeQuery = true)
    List<IWarehouseProductCountDTO> countProductQuantityByProductCode(@Param("productCode") String productCode);

}
