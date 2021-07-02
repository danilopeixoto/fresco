package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Warehouse;
import com.mercadolibre.fresco.model.WarehouseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseSectionRepository extends JpaRepository<WarehouseSection, Long> {

    @Query(value = "SELECT * FROM warehouse_section WHERE warehouse_id = :warehouseId AND section_id = :sectionId", nativeQuery = true)
    WarehouseSection findByWarehouseAndSectionId(@Param("warehouseId") Long warehouseId,
                                        @Param("sectionId") Long sectionId);

}
