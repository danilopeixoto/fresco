package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.model.WarehouseSection;
import org.springframework.data.repository.query.Param;

public interface IWarehouseSection extends ICRUD<WarehouseSection> {

    WarehouseSection findByWarehouseAndSectionId(@Param("warehouseId") Long warehouseId,
                                        @Param("sectionId") Long sectionId);

}
