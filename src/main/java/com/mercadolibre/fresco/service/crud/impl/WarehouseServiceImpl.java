package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Warehouse;
import com.mercadolibre.fresco.repository.WarehouseRepository;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    private WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    @Transactional
    public Warehouse create(Warehouse warehouse) {
        if (warehouseRepository.findByWarehouseCode(warehouse.getWarehouseCode()) != null){
            throw new BadRequestException("Warehouse already exists!");
        }
        return warehouseRepository.save(warehouse);
    }

    @Override
    @Transactional
    public Warehouse update(Warehouse warehouse) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!warehouseRepository.findById(id).isPresent()){
            throw new NotFoundException("Warehouse not vound");
        }
        warehouseRepository.deleteById(id);
    }

    @Override
    public Warehouse findById(String id) {
        Warehouse warehouse = warehouseRepository.findById(id);
        if (warehouse == null){
            throw new NotFoundException("Warehouse not existis");
        }
        return warehouse;
    }

    @Override
    public Warehouse findById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).get();
        if (warehouse == null){
            throw new NotFoundException("Warehouse not existis");
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> findAll() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()){
            throw new NotFoundException("Warehouses not found!");
        }
        return warehouses;
    }
}
