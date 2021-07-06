package com.mercadolibre.fresco.service.impl;

import com.google.common.collect.Streams;
import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
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
    public InboundOrderResponseDTO create(String username, InboundOrderDTO inboundOrderDTO) throws ApiException {
        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(sectionDTO.getWarehouseCode());

        if (warehouse == null) {
            throw new ApiException("404", "Warehouse not found.", 404);
        }

        if (!warehouse.getAgent().getUsername().equals(username)) {
            throw new ApiException("401", "Agent not allowed.", 401);
        }

        WarehouseSection warehouseSection = warehouse.getWarehouseSection();
        Section section = warehouseSection.getSection();

        if (!section.getSectionCode().equals(sectionDTO.getSectionCode())) {
            throw new ApiException("400", "Invalid warehouse section.", 400);
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

        long totalQuantity = stocks
                .stream()
                .map(stock -> stock.getCurrentQuantity().longValue())
                .reduce(0L, Long::sum);

        if (totalQuantity + warehouseSection.getQuantity() > warehouseSection.getCapacity()) {
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
    public InboundOrderResponseDTO update(String username, InboundOrderDTO inboundOrderDTO) throws ApiException {
        SectionDTO sectionDTO = inboundOrderDTO.getSection();
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(sectionDTO.getWarehouseCode());

        if (warehouse == null) {
            throw new ApiException("404", "Warehouse not found.", 404);
        }

        if (!warehouse.getAgent().getUsername().equals(username)) {
            throw new ApiException("401", "Agent not allowed.", 401);
        }

        WarehouseSection warehouseSection = warehouse.getWarehouseSection();
        Section section = warehouseSection.getSection();

        if (!section.getSectionCode().equals(sectionDTO.getSectionCode())) {
            throw new ApiException("400", "Invalid warehouse section.", 400);
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
            throw new ApiException("404", "Some stock not found.", 404);
        }

        return this.create(username, inboundOrderDTO);
    }
}
