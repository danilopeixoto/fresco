package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.model.Warehouse;
import com.mercadolibre.fresco.repository.WarehouseRepository;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Warehouse create(Warehouse warehouse) {
        return this.warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse update(Warehouse warehouse) {
        return null;
    }

    @Override
    public void delete(Long id) {
        this.warehouseRepository.deleteById(id);
    }

    @Override
    public Long getWarehouseIdByCode(String warehouseCode) {
        warehouseCode = warehouseCode.toUpperCase();
        return this.warehouseRepository.getWarehouseIdByCode(warehouseCode);
    }

    @Override
    public Warehouse findById(Long id) {
        return this.warehouseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Warehouse> findAll() {
        return this.warehouseRepository.findAll();
    }
}
