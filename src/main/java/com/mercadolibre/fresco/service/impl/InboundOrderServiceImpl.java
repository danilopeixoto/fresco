package com.mercadolibre.fresco.service.impl;

import com.google.common.collect.Streams;
import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.*;
import com.mercadolibre.fresco.repository.SectionRepository;
import com.mercadolibre.fresco.repository.WarehouseSectionRepository;
import com.mercadolibre.fresco.service.IInboundOrderService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IStockService;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InboundOrderServiceImpl implements IInboundOrderService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private final Integer MAX_CAPACITY = 10;

    private IWarehouseService warehouseService;
    private IProductService productService;
    private IStockService stockService;
    private WarehouseSectionRepository warehouseSectionRepository;
    private SectionRepository sectionRepository;

    public InboundOrderServiceImpl(IWarehouseService warehouseService, IProductService productService,
                                   IStockService stockService, WarehouseSectionRepository warehouseSectionRepository,
                                   SectionRepository sectionRepository) {
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.stockService = stockService;
        this.warehouseSectionRepository = warehouseSectionRepository;
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    @Override
    public InboundOrderResponseDTO create(String username, InboundOrderDTO inboundOrderDTO) throws ApiException, NotFoundException {

        this.validateAndPersistRequestForCreateInboundOrder(username, inboundOrderDTO);

        return InboundOrderResponseDTO
            .builder()
            .batchStock(inboundOrderDTO.getBatchStock())
            .build();
    }

    @Transactional
    @Override
    public InboundOrderResponseDTO update(String username, InboundOrderDTO inboundOrderDTO) throws ApiException, NotFoundException {
        this.validateRequestForUpdateInboundOrder(username, inboundOrderDTO);

        inboundOrderDTO.getBatchStock()
            .forEach(
                stockDTO -> this.buildAndSaveUpdatedStock(stockDTO, stockDTO.getBatchNumber())
            );


        return InboundOrderResponseDTO
            .builder()
            .batchStock(inboundOrderDTO.getBatchStock())
            .build();
    }

    private void validateRequestForUpdateInboundOrder(String username, InboundOrderDTO inboundOrderDTO) {
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(inboundOrderDTO.getSection().getWarehouseCode());
        WarehouseSection warehouseSection = this.warehouseSectionRepository
            .findByWarehouseAndSectionCode(inboundOrderDTO.getSection().getWarehouseCode(), inboundOrderDTO.getSection().getSectionCode());

        // Validate agent belongs this warehouse
        this.validateAgentBelongsThisWarehouse(username, warehouse);

        //Validate warehouse section exists
        this.validateWarehouseSectionExists(warehouseSection);
    }

    private void buildAndSaveUpdatedStock(StockDTO stockDTO, Integer batchNumber) {

        Stock stock = this.stockService.findByBatchNumber(batchNumber);
        stock.setCurrentQuantity(stockDTO.getCurrentQuantity());
        stock.setCurrentTemperature(stockDTO.getCurrentTemperature());

        this.stockService.create(stock);
    }

    public void validateAgentBelongsThisWarehouse(String agentUsername, Warehouse warehouse) {
        if (!warehouse.getAgent().getUsername().equals(agentUsername)) {
            throw new ApiException("401", "Agent not allowed.", 401);
        }
    }

    public void validateWarehouseSectionExists(WarehouseSection warehouseSection) {
        if (warehouseSection == null) {
            throw new ApiException("400", "Invalid warehouse section.", 400);
        }
    }

    public List<Product> validateRequestProductsAreAvailable(List<StockDTO> stocks) {
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

        return products;
    }

    public void validateProductsCategoriesAndSectionsMatches(List<Product> products, InboundOrderDTO inboundOrderDTO, Section section) {
        if (products
            .stream()
            .filter(product -> product
                .getProductCategory()
                .getCategoryCode()
                .equals(section
                    .getProductCategory()
                    .getCategoryCode()))
            .count() != inboundOrderDTO.getBatchStock().size()) {
            throw new ApiException("400", "Some product category and section mismatched.", 400);
        }
    }

    public void validateThatIsSpaceAvailable(WarehouseSection warehouseSection) {
        if (stockService.countStocksOnSection(warehouseSection.getId()) > this.MAX_CAPACITY) {
            throw new ApiException("400", "Stock maximum capacity reached.", 400);
        }
    }

    public void persistRequestBatchStocks(InboundOrderDTO inboundOrderDTO, List<Product> products, WarehouseSection warehouseSection) {
        try {
            Streams
                .zip(
                    inboundOrderDTO.getBatchStock().stream(),
                    products.stream(),
                    AbstractMap.SimpleEntry::new)
                .map(s -> Stock
                    .builder()
                    .batchNumber(s.getKey().getBatchNumber())
                    .currentQuantity(s.getKey().getCurrentQuantity())
                    .currentTemperature(s.getKey().getCurrentTemperature())
                    .dueDate(s.getKey().getDueDate())
                    .manufacturingDate(s.getKey().getManufacturingDate())
                    .manufacturingTime(s.getKey().getManufacturingTime())
                    .initialQuantity(s.getKey().getInitialQuantity())
                    .warehouseSection(warehouseSection)
                    .product(s.getValue())
                    .build())
                .forEach(s -> this.stockService.create(s));
        } catch (DataIntegrityViolationException e) {
            throw new ApiException("400", "Batch number already exists.", 400);
        }
    }

    private void validateAndPersistRequestForCreateInboundOrder(String username, InboundOrderDTO inboundOrderDTO) {
        Warehouse warehouse = this.warehouseService.findWarehouseByCode(inboundOrderDTO.getSection().getWarehouseCode());

        // Validate warehouse agent
        this.validateAgentBelongsThisWarehouse(username, warehouse);

        WarehouseSection warehouseSection = this.warehouseSectionRepository
            .findByWarehouseAndSectionCode(inboundOrderDTO.getSection().getWarehouseCode(), inboundOrderDTO.getSection().getSectionCode());

        // Validate warehouse exists
        this.validateWarehouseSectionExists(warehouseSection);

        Section section = sectionRepository.getBySectionCode(inboundOrderDTO.getSection().getSectionCode());

        // Validate products are available
        List<Product> products = this.validateRequestProductsAreAvailable(inboundOrderDTO.getBatchStock());

        // Validate products categories and sections matches
        this.validateProductsCategoriesAndSectionsMatches(products, inboundOrderDTO, section);

        // Validate that is space available in warehouse section
        this.validateThatIsSpaceAvailable(warehouseSection);

        // Persist request in database
        this.persistRequestBatchStocks(inboundOrderDTO, products, warehouseSection);
    }

}
