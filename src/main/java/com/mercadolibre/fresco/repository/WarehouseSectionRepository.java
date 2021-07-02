package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.WarehouseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseSectionRepository extends JpaRepository<WarehouseSection, Long> {
    WarehouseSection findByWarehouseWarehouseCodeAndSectionSectionCode(Long warehouseCode, Long sectionCode);
}
