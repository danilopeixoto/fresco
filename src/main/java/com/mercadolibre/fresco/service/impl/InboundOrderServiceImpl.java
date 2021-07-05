package com.mercadolibre.fresco.service.impl;

import com.google.common.collect.Streams;
import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.exceptions.UnauthorizedException;
import com.mercadolibre.fresco.model.*;
import com.mercadolibre.fresco.service.IInboundOrderService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IStockService;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InboundOrderServiceImpl implements IInboundOrderService {
    private IWarehouseService warehouseService;
    private IProductService productService;
    private IStockService stockService;

    public InboundOrderServiceImpl(IWarehouseService warehouseService, IProductService productService) {
        this.warehouseService = warehouseService;
        this.productService = productService;
    }

    @Transactional
    @Override
    public InboundOrderResponseDTO create(String username, InboundOrderDTO inboundOrderDTO)
            throws NotFoundException, BadRequestException, UnauthorizedException {
        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(sectionDTO.getWarehouseCode());

        if (warehouse == null) {
            throw new NotFoundException("Warehouse not found.");
        }

        if (!warehouse.getAgent().getUsername().equals(username)) {
            throw new UnauthorizedException("Agent not allowed.");
        }

        WarehouseSection warehouseSection = warehouse.getWarehouseSection();
        Section section = warehouseSection.getSection();

        if (!section.getSectionCode().equals(sectionDTO.getSectionCode())) {
            throw new BadRequestException("Invalid warehouse section.");
        }

        List<StockDTO> stocks = inboundOrderDTO.getBatchStock();

        List<Product> products = stocks
                .stream()
                .map(StockDTO::getProductCode)
                .map(code -> this.productService.findByProductCode(code))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        long stockSize = stocks.size();

        if (stockSize != products.size()) {
            throw new NotFoundException("Some product not found.");
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
            throw new BadRequestException("Some product category and section mismatched.");
        }

        long totalQuantity = stocks
                .stream()
                .map(stock -> stock.getCurrentQuantity().longValue())
                .reduce(0L, Long::sum);

        if (totalQuantity + warehouseSection.getQuantity() > warehouseSection.getCapacity()) {
            throw new BadRequestException("Stock maximum capacity reached.");
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
            throw new BadRequestException("Batch number already exists.");
        }

        return InboundOrderResponseDTO
                .builder()
                .batchStock(stocks)
                .build();
    }

    @Transactional
    @Override
    public InboundOrderResponseDTO update(String username, InboundOrderDTO inboundOrderDTO)
            throws NotFoundException, BadRequestException, UnauthorizedException {
        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(sectionDTO.getWarehouseCode());

        if (warehouse == null) {
            throw new NotFoundException("Warehouse not found.");
        }

        if (!warehouse.getAgent().getUsername().equals(username)) {
            throw new UnauthorizedException("Agent not allowed.");
        }

        WarehouseSection warehouseSection = warehouse.getWarehouseSection();
        Section section = warehouseSection.getSection();

        if (!section.getSectionCode().equals(sectionDTO.getSectionCode())) {
            throw new BadRequestException("Invalid warehouse section.");
        }

        List<Integer> batchNumbers = inboundOrderDTO
                .getBatchStock()
                .stream()
                .map(StockDTO::getBatchNumber)
                .collect(Collectors.toList());

        try {
            batchNumbers
                    .forEach(bn -> this.stockService.deleteByBatchNumber(bn));
        } catch (Exception e) {
            throw new NotFoundException("Some stock not found.");
        }

        return this.create(username, inboundOrderDTO);
    }
}
