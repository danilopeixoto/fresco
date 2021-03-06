package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.WarehousesProductCountResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IWarehouseProductCountDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.repository.WarehouseRepository;
import com.mercadolibre.fresco.service.IWarehouseCatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseCatalogServiceImpl implements IWarehouseCatalogService {
    private WarehouseRepository warehouseRepository;

    public WarehouseCatalogServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public WarehousesProductCountResponseDTO groupByWarehouseCodeCountByProductCode(String productCode) {
        if (warehouseRepository.countProductQuantityByProductCode(productCode) == null) {
            throw new ApiException("404", "Product " + productCode + " not exists!", 404);
        }

        List<IWarehouseProductCountDTO> warehouses = this.warehouseRepository.countProductQuantityByProductCode(productCode);

        if (warehouses.isEmpty()) {
            throw new ApiException("404", "Product " + productCode + " not exists in warehouses!", 404);
        }

        WarehousesProductCountResponseDTO warehousesProductCountResponseDTO = new WarehousesProductCountResponseDTO(
            productCode,
            warehouses
        );

        return warehousesProductCountResponseDTO;
    }
}
