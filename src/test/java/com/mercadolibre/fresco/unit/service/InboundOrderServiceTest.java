package com.mercadolibre.fresco.unit.service;


import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.*;
import com.mercadolibre.fresco.repository.*;
import com.mercadolibre.fresco.service.crud.impl.ProductServiceImpl;
import com.mercadolibre.fresco.service.crud.impl.StockServiceImpl;
import com.mercadolibre.fresco.service.crud.impl.WarehouseServiceImpl;
import com.mercadolibre.fresco.service.impl.InboundOrderServiceImpl;
import com.mercadolibre.fresco.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class InboundOrderServiceTest {
    static final String NOT_ALLOWED_AGENT_ON_WAREHOUSE = "Agent not allowed.";
    static final String NOT_VALID_WAREHOUSE_SECTION = "Invalid warehouse section.";

     InboundOrderServiceImpl inboundOrderService;
     WarehouseServiceImpl warehouseService;
     ProductServiceImpl productService;
     StockServiceImpl stockService;
     WarehouseRepository warehouseRepository = Mockito.mock(WarehouseRepository.class);
     WarehouseSectionRepository warehouseSectionRepository = Mockito.mock(WarehouseSectionRepository.class);
     SectionRepository sectionRepository = Mockito.mock(SectionRepository.class);
     StockRepository stockRepository = Mockito.mock(StockRepository.class);
     OrderedProductRepository orderedProductRepository = Mockito.mock(OrderedProductRepository.class);
     ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    @BeforeEach
    public void setup(){

        this.warehouseService = new WarehouseServiceImpl(warehouseRepository);
        this.productService = new ProductServiceImpl(productRepository);
        this.stockService = new StockServiceImpl(stockRepository, orderedProductRepository);
        this.inboundOrderService = new InboundOrderServiceImpl(warehouseService,productService,stockService,warehouseSectionRepository,sectionRepository);
    }

    @Test
    void shouldCreateAnInboundOrder() {
        InboundOrderDTO inboundOrderDTO = TestUtil.createInboundOrderDTO();
        Product product = TestUtil.createProduct();
        Warehouse warehouse = TestUtil.createWarehouse();
        Section section = TestUtil.createSection();
        WarehouseSection warehouseSection = TestUtil.createWarehouseSection();

        when(this.warehouseRepository.findWarehouseByCode("WAREHOUSE_TESTE")).thenReturn(warehouse);
        when(this.warehouseSectionRepository.findByWarehouseAndSectionCode("WAREHOUSE_TESTE", "FS")).thenReturn(warehouseSection);
        when(this.productRepository.findByProductCode("BANANA")).thenReturn(product);
        when(this.sectionRepository.getBySectionCode("FS")).thenReturn(section);

        InboundOrderResponseDTO inboundOrderResponseDTO  = this.inboundOrderService.create("testRep", inboundOrderDTO);
        assertEquals(inboundOrderResponseDTO.getBatchStock().get(0).getBatchNumber(), 25);
    }

    @Test
    void shouldUpdateAnInboundOrder() {
        InboundOrderDTO inboundOrderDTO = TestUtil.createInboundOrderDTO();
        Product product = TestUtil.createProduct();
        Warehouse warehouse = TestUtil.createWarehouse();
        Section section = TestUtil.createSection();
        Stock stock = TestUtil.createStock();
        WarehouseSection warehouseSection = TestUtil.createWarehouseSection();

        when(this.warehouseRepository.findWarehouseByCode("WAREHOUSE_TESTE")).thenReturn(warehouse);
        when(this.warehouseSectionRepository.findByWarehouseAndSectionCode("WAREHOUSE_TESTE", "FS")).thenReturn(warehouseSection);
        when(this.stockRepository.findByBatchNumber(25)).thenReturn(stock);

        InboundOrderResponseDTO inboundOrderResponseDTO  = this.inboundOrderService.update("testRep", inboundOrderDTO);
        assertEquals(inboundOrderResponseDTO.getBatchStock().get(0).getBatchNumber(), 25);
    }


    @Test
    void shouldNotValidateAgentBelongsThisWarehouse() {
        Warehouse warehouse = TestUtil.createWarehouse();

        ApiException e = assertThrows(ApiException.class, () -> this.inboundOrderService.validateAgentBelongsThisWarehouse("testAdmin", warehouse));
        assertEquals(NOT_ALLOWED_AGENT_ON_WAREHOUSE, e.getMessage());
    }

    @Test
    void shouldNotValidateWarehouseSectionExists() {

        ApiException e = assertThrows(ApiException.class, () -> this.inboundOrderService.validateWarehouseSectionExists(null));
        assertEquals(NOT_VALID_WAREHOUSE_SECTION, e.getMessage());
    }

//    @Test
//    void shouldNotValidateRequestProductsAreAvailable() {
//
//        List<Stock> stocks = TestUtil.createListStock();
//
//        ApiException e = assertThrows(ApiException.class, () -> this.inboundOrderService.validateRequestProductsAreAvailable());
//        assertEquals(NOT_VALID_WAREHOUSE_SECTION, e.getMessage());
//    }


}

