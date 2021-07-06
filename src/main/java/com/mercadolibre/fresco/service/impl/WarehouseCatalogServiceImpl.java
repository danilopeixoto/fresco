package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.WarehouseProductCountResponseDTO;
import com.mercadolibre.fresco.dtos.response.WarehousesProductCountResponseDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
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
            throw new NotFoundException("Product " + productCode + " not exists!");
        }

        List<WarehouseProductCountResponseDTO> warehouses = this.warehouseRepository.countProductQuantityByProductCode(productCode);

        if (warehouses.isEmpty()) {
            throw new NotFoundException("Product " + productCode + " not exists in warehouses!");
        }

        WarehousesProductCountResponseDTO warehousesProductCountResponseDTO = new WarehousesProductCountResponseDTO(
                productCode,
                warehouses
        );

        return warehousesProductCountResponseDTO;
    }
}
