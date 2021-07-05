package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.model.WarehouseSection;
import com.mercadolibre.fresco.repository.WarehouseSectionRepository;
import com.mercadolibre.fresco.service.crud.IWarehouseSection;

import java.util.List;

public class WarehouseSectionImpl implements IWarehouseSection {

    private final WarehouseSectionRepository warehouseSectionRepository;

    public WarehouseSectionImpl(WarehouseSectionRepository warehouseSectionRepository) {
        this.warehouseSectionRepository = warehouseSectionRepository;
    }

    @Override
    public WarehouseSection create(WarehouseSection warehouseSection) {
        return this.warehouseSectionRepository.save(warehouseSection);
    }

    @Override
    public WarehouseSection update(WarehouseSection warehouseSection) {
        return null;
    }

    @Override
    public void delete(Long id) {
        this.warehouseSectionRepository.deleteById(id);
    }

    @Override
    public WarehouseSection findById(Long id) {
        return this.warehouseSectionRepository.findById(id).orElse(null);
    }

    @Override
    public List<WarehouseSection> findAll() {
        return null;
    }

    @Override
    public WarehouseSection findByWarehouseAndSectionId(Long warehouseId, Long sectionId) {
        return this.warehouseSectionRepository.findByWarehouseAndSectionId(warehouseId, sectionId);
    }
}
