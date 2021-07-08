package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.config.ModelMapperConfig;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductStockResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IInfoStockDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.impl.BatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.impl.InfoStockDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.model.ProductCategory;
import com.mercadolibre.fresco.model.enumeration.BatchStockOrder;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.repository.OrderedProductRepository;
import com.mercadolibre.fresco.repository.ProductRepository;
import com.mercadolibre.fresco.repository.StockRepository;
import com.mercadolibre.fresco.service.IProductCatalogService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IStockService;
import com.mercadolibre.fresco.service.crud.impl.ProductServiceImpl;
import com.mercadolibre.fresco.service.crud.impl.StockServiceImpl;
import com.mercadolibre.fresco.service.impl.ProductCatalogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

public class ProductCatalogServiceImplTest {
    static final String NOT_FOUND_PRODUCT_LIST = "Product list not found.";
    static final String NOT_FOUND_PRODUCT = "Product not found.";

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    StockRepository stockRepository = Mockito.mock(StockRepository.class);
    OrderedProductRepository orderedProductRepository = Mockito.mock(OrderedProductRepository.class);

    ModelMapperConfig modelMapperConfig;
    IProductService productService;
    IStockService stockService;
    IProductCatalogService productCatalogService;

    @BeforeEach
    void setup() {
        this.modelMapperConfig = new ModelMapperConfig();
        this.productService = new ProductServiceImpl(this.productRepository);
        this.stockService = new StockServiceImpl(this.stockRepository, this.orderedProductRepository);
        this.productCatalogService = new ProductCatalogServiceImpl(this.productService, this.modelMapperConfig.modelMapper(), this.stockService);
    }

    @Test
    void shouldFindAllThrowNotFoundException() {
        when(this.productRepository.findAll()).thenReturn(List.<Product>of());

        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> this.productCatalogService.findAll());

        assertEquals("Not Found Exception. " + NOT_FOUND_PRODUCT_LIST, exception.getMessage());
    }

    @Test
    void shouldFindAllListOneItem() {
        Product product = new Product(1L, "BANANA", null, null, null,
            null, null, null, null, new ProductCategory(1L, "FS", "Fresh", null, null));

        when(this.productRepository.findAll()).thenReturn(List.<Product>of(product));
        List<ProductResponseDTO> responses = this.productCatalogService.findAll();

        assertEquals(responses.size(), 1);

        ProductResponseDTO response = responses.get(0);

        assertEquals(response.getId(), product.getId());
        assertEquals(response.getProductCode(), product.getProductCode());
        assertEquals(response.getProductCategory().getCategoryCode(), product.getProductCategory().getCategoryCode());
    }

    @Test
    void shouldFindProductsByCategoryCodeThrowNotFoundException() {
        EProductCategory categoryCode = EProductCategory.FS;

        when(this.productRepository.findByProductCategory(categoryCode.name())).thenReturn(List.<Product>of());

        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> this.productCatalogService.findProductsByCategoryCode(categoryCode));

        assertEquals("Not Found Exception. " + NOT_FOUND_PRODUCT_LIST, exception.getMessage());
    }

    @Test
    void shouldFindProductsByCategoryCodeListOneItem() {
        EProductCategory categoryCode = EProductCategory.FS;

        Product product = new Product(1L, "BANANA", null, null, null,
            null, null, null, null, new ProductCategory(1L, categoryCode.name(), "Fresh", null, null));

        when(this.productRepository.findByProductCategory(categoryCode.name())).thenReturn(List.<Product>of(product));
        List<ProductResponseDTO> responses = this.productCatalogService.findProductsByCategoryCode(categoryCode);

        assertEquals(responses.size(), 1);

        ProductResponseDTO response = responses.get(0);

        assertEquals(response.getId(), product.getId());
        assertEquals(response.getProductCode(), product.getProductCode());
        assertEquals(response.getProductCategory().getCategoryCode(), product.getProductCategory().getCategoryCode());
    }

    @Test
    void shouldFindStocksByProductCodeThrowNotFoundException() {
        String username = "testeRep";
        String productCode = "PRODUCT_CODE";
        BatchStockOrder batchStockOrder = BatchStockOrder.C;

        when(this.stockRepository.findWithSectionAndWarehouseByProductCode(username, productCode))
            .thenReturn(List.<IInfoStockDTO>of());

        ApiException exception = assertThrows(
            ApiException.class,
            () -> this.productCatalogService.findStocksByProductCode(username, productCode, batchStockOrder));

        assertEquals(404, exception.getStatusCode());
        assertEquals(NOT_FOUND_PRODUCT, exception.getMessage());
    }

    @Test
    void shouldFindStocksByProductCodeListOneItem() {
        String username = "testeRep";
        String productCode = "PRODUCT_CODE";
        BatchStockOrder batchStockOrder = BatchStockOrder.C;

        InfoStockDTO infoStock = new InfoStockDTO(1, 1, null, "SECTION_CODE", "WAREHOUSE_CODE");

        when(this.stockRepository.findWithSectionAndWarehouseByProductCode(username, productCode))
            .thenReturn(Arrays.<IInfoStockDTO>asList(infoStock));

        ProductStockResponseDTO response = this.productCatalogService.findStocksByProductCode(username, productCode, batchStockOrder);
        assertEquals(response.getProductId(), productCode);

        List<IInfoStockDTO> stockResponses = response.getBatchStock();
        assertEquals(stockResponses.size(), 1);

        IInfoStockDTO stock = stockResponses.get(0);

        assertEquals(stock.getBatchNumber(), infoStock.getBatchNumber());
        assertEquals(stock.getCurrentQuantity(), infoStock.getCurrentQuantity());
        assertEquals(stock.getSectionCode(), infoStock.getSectionCode());
        assertEquals(stock.getWarehouseCode(), infoStock.getWarehouseCode());
    }

    @Test
    void shouldFindStocksByDueDateThrowNotFoundException() {
        int days = 10;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        when(this.stockRepository.findStockWithProductDueDateUntilFutureDate(date.format(formatter)))
            .thenReturn(List.<IBatchStockDueDateResponseDTO>of());

        ApiException exception = assertThrows(
            ApiException.class,
            () -> this.productCatalogService.findStocksByDueDate(days));

        assertEquals(404, exception.getStatusCode());
        assertEquals(NOT_FOUND_PRODUCT_LIST, exception.getMessage());
    }

    @Test
    void shouldFindStocksByDueDateListOne() {
        int days = 10;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        BatchStockDueDateResponseDTO batchStockDueDate = new BatchStockDueDateResponseDTO(
            "BATCH_NUMBER", "PRODUCT_ID", "PRODUCT_TYPE_ID", date, 100);

        when(this.stockRepository.findStockWithProductDueDateUntilFutureDate(date.format(formatter)))
            .thenReturn(List.<IBatchStockDueDateResponseDTO>of(batchStockDueDate));

        List<IBatchStockDueDateResponseDTO> responses = this.productCatalogService.findStocksByDueDate(days);

        assertEquals(responses.size(), 1);

        IBatchStockDueDateResponseDTO response = responses.get(0);

        assertEquals(response.getBatchNumber(), batchStockDueDate.getBatchNumber());
        assertEquals(response.getDueDate(), batchStockDueDate.getDueDate());
        assertEquals(response.getProductId(), batchStockDueDate.getProductId());
        assertEquals(response.getQuantity(), batchStockDueDate.getQuantity());
        assertEquals(response.getProductTypeId(), batchStockDueDate.getProductTypeId());
    }
}
