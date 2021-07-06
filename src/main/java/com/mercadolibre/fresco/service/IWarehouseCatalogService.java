package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.response.WarehousesProductCountResponseDTO;

public interface IWarehouseCatalogService {
    WarehousesProductCountResponseDTO groupByWarehouseCodeCountByProductCode(String productCode);
}
