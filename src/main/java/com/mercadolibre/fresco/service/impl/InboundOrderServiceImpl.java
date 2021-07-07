package com.mercadolibre.fresco.service.impl;

import com.google.common.collect.Streams;
import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.*;
import com.mercadolibre.fresco.model.enumeration.BatchStockOrder;
import com.mercadolibre.fresco.repository.SectionRepository;
import com.mercadolibre.fresco.repository.WarehouseSectionRepository;
import com.mercadolibre.fresco.service.IInboundOrderService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IStockService;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InboundOrderServiceImpl implements IInboundOrderService {
    private final Integer MAXCAPACITY = 10;
    private IWarehouseService warehouseService;
    private IProductService productService;
    private IStockService stockService;
    private WarehouseSectionRepository warehouseSectionRepository;
    private SectionRepository sectionRepository;

    public InboundOrderServiceImpl(IWarehouseService warehouseService, IProductService productService, IStockService stockService, WarehouseSectionRepository warehouseSectionRepository, SectionRepository sectionRepository) {
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.stockService = stockService;
        this.warehouseSectionRepository = warehouseSectionRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    @Override
    public InboundOrderResponseDTO create(String username, InboundOrderDTO inboundOrderDTO) throws ApiException, NotFoundException {
        
        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(sectionDTO.getWarehouseCode());
        WarehouseSection warehouseSection = this.warehouseSectionRepository.findByWarehouseAndSectionCode(sectionDTO.warehouseCode, sectionDTO.sectionCode);

        if (!warehouse.getAgent().getUsername().equals(username)) {
            throw new ApiException("401", "Agent not allowed.", 401);
        }

        if( warehouseSection == null){
            throw new ApiException("400", "Invalid warehouse section.", 400);
        }

        Section section = sectionRepository.getBySectionCode(sectionDTO.sectionCode);

        List<StockDTO> stocks = inboundOrderDTO.getBatchStock();

        List<Product> products = stocks
            .stream()
            .map(StockDTO::getProductCode)
            .map(code -> this.productService.findByProductCode(code))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        long stockSize = stocks.size();

        if (stockSize != products.size()) {
            throw new ApiException("404", "Some product not found.", 404);
        }

        if (products
            .stream()
            .filter(product -> product
                .getProductCategory()
                .getCategoryCode()
                .equals(section
                    .getProductCategory()
                    .getCategoryCode()))
            .count() != stockSize) {
            throw new ApiException("400", "Some product category and section mismatched.", 400);
        }


        if (stockService.countStocksOnSection(warehouseSection.getId()) > MAXCAPACITY) {
            throw new ApiException("400", "Stock maximum capacity reached.", 400);
        }

        try {
            Streams
                .zip(
                    stocks.stream(),
                    products.stream(),
                    AbstractMap.SimpleEntry::new)
                .map(s -> Stock
                    .builder()
                    .batchNumber(s.getKey().getBatchNumber())
                    .currentQuantity(s.getKey().getCurrentQuantity())
                    .currentTemperature(s.getKey().getCurrentTemperature())
                    .initialQuantity(s.getKey().getInitialQuantity())
                    .warehouseSection(warehouseSection)
                    .product(s.getValue())
                    .build())
                .forEach(s -> this.stockService.create(s));
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", "Batch number already exists.", 400);
        }

        return InboundOrderResponseDTO
            .builder()
            .batchStock(stocks)
            .build();
    }

    @Transactional
    @Override
    public InboundOrderResponseDTO update(String username, InboundOrderDTO inboundOrderDTO) throws ApiException, NotFoundException {
        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(sectionDTO.getWarehouseCode());
        WarehouseSection warehouseSection = this.warehouseSectionRepository.findByWarehouseAndSectionCode(sectionDTO.warehouseCode, sectionDTO.sectionCode);

        if (!warehouse.getAgent().getUsername().equals(username)) {
            throw new ApiException("401", "Agent not allowed.", 401);
        }

        if( warehouseSection == null){
            throw new ApiException("400", "Invalid warehouse section.", 400);
        }

        inboundOrderDTO.getBatchStock().forEach(stockDTO -> this.buildUpdatedStock(stockDTO, stockService.findByBatchNumber(stockDTO.getBatchNumber())));


        return this.create(username, inboundOrderDTO);
    }

    private Stock buildUpdatedStock(StockDTO stockDTO, Stock stock){
        stock.setCurrentQuantity(stockDTO.getCurrentQuantity());
        stock.setCurrentTemperature(stockDTO.getCurrentTemperature());

        return stock;
    }
}
