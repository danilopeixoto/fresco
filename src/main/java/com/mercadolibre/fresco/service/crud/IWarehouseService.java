package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.model.Warehouse;
import org.springframework.data.repository.query.Param;

public interface IWarehouseService extends ICRUD<Warehouse> {

    Long getWarehouseIdByCode(@Param("warehouseCode") String warehouseCode);

    Warehouse findWarehouseByCode(@Param("warehouseCode") String warehouseCode);
}
