package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.WarehouseSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseSectionRepository extends JpaRepository<WarehouseSection, Long> {
    public WarehouseSection findByWarehouseCodeAndSectionCode();
}
