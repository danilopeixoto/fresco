package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.NotFoundException;
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
  public Warehouse findWarehouseByCode(String code) {
    Warehouse warehouse = this.warehouseRepository.findWarehouseByCode(code);
    if (warehouse == null) {
      throw new NotFoundException("Warehouse with code " + code + " not found!");
    }
    return warehouse;
  }

  @Override
  public Long getWarehouseIdByCode(String warehouseCode) {
    warehouseCode = warehouseCode.toUpperCase();
    if (warehouseCode == null) {
      throw new NotFoundException("warehouse id not found!");
    }
    return this.warehouseRepository.getWarehouseIdByCode(warehouseCode);
  }

  @Override
  public Warehouse findById(Long id) {
    Warehouse warehouse = this.warehouseRepository.findById(id).orElse(null);
    if (warehouse == null) {
      throw new NotFoundException("Warehouse with id " + id + " not found!");
    }
    return warehouse;
  }

  @Override
  public List<Warehouse> findAll() {
    List<Warehouse> warehouses = this.warehouseRepository.findAll();
    if (warehouses.isEmpty()) {
      throw new NotFoundException("Warehouses not found!");
    }
    return warehouses;
  }
}
