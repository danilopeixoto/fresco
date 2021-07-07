package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.WarehouseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseSectionRepository extends JpaRepository<WarehouseSection, Long> {

    @Query(value = "SELECT * FROM warehouse_section WHERE warehouse_id = :warehouseId AND section_id = :sectionId", nativeQuery = true)
    WarehouseSection findByWarehouseAndSectionId(@Param("warehouseId") Long warehouseId, @Param("sectionId") Long sectionId);

    @Query(value = "SELECT ws.* FROM warehouse_section as ws " +
        "INNER JOIN sections as s ON s.id = ws.section_id " +
        "INNER JOIN warehouses as w ON w.id = ws.warehouse_id " +
        "WHERE s.section_code = :sectionCode AND w.warehouse_code = :warehouseCode", nativeQuery = true)
    WarehouseSection findByWarehouseAndSectionCode(@Param("warehouseCode") String warehouseCode, @Param("sectionCode") String sectionCode);

}
