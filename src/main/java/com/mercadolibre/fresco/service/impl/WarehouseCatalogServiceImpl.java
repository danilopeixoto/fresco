package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.WarehousesProductCountResponseDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.service.IWarehouseCatalogService;

public class WarehouseCatalogServiceImpl implements IWarehouseCatalogService {
    @Override
    public WarehousesProductCountResponseDTO groupByWarehouseCodeCountByProductCode(String productCode)
            throws NotFoundException {
        return null;
    }
}
