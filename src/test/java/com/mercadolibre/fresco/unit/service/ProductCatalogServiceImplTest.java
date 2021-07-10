package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.config.ModelMapperConfig;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductStockResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IInfoStockDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.impl.BatchStockDueDateResponseDTOImpl;
import com.mercadolibre.fresco.dtos.response.aggregation.impl.InfoStockDTOImpl;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.model.ProductCategory;
import com.mercadolibre.fresco.model.enumeration.BatchStockOrder;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.model.enumeration.EResultOrder;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ProductCatalogServiceImplTest {
    static final String NOT_FOUND_PRODUCT_LIST = "Products not exists!";
    static final String NOT_FOUND_PRODUCT = "Products not found.";

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

        ApiException exception = assertThrows(
            ApiException.class,
            () -> this.productCatalogService.findAll());

        assertEquals(exception.getMessage(), NOT_FOUND_PRODUCT_LIST);
    }

    @Test
    void shouldFindAllListOneItem() {
        Product product = new Product(1L, "BANANA", null, 5., null, null
            , new ProductCategory(1L, "FS", "Fresh", null, null));

        when(this.productRepository.findAll()).thenReturn(List.<Product>of(product));
        List<ProductResponseDTO> responses = this.productCatalogService.findAll();

        assertEquals(1, responses.size());

        ProductResponseDTO response = responses.get(0);

        assertEquals(product.getId(), response.getId());
        assertEquals(product.getProductCode(), response.getProductCode());
        assertEquals(product.getProductCategory().getCategoryCode(), response.getProductCategory().getCategoryCode());
    }

    @Test
    void shouldFindProductsByCategoryCodeThrowNotFoundException() {
        EProductCategory categoryCode = EProductCategory.FS;

        when(this.productRepository.findByProductCategory(categoryCode.name())).thenReturn(List.<Product>of());

        ApiException exception = assertThrows(
            ApiException.class,
            () -> this.productCatalogService.findProductsByCategoryCode(categoryCode));

        assertEquals(exception.getMessage(), "Products in category " + categoryCode.name() + " not exists!");
    }

    @Test
    void shouldFindProductsByCategoryCodeListOneItem() {
        EProductCategory categoryCode = EProductCategory.FS;

        Product product = new Product(1L, "BANANA", null, 5., null, null
            , new ProductCategory(1L, categoryCode.name(), "Fresh", null, null));

        when(this.productRepository.findByProductCategory(categoryCode.name())).thenReturn(List.<Product>of(product));
        List<ProductResponseDTO> responses = this.productCatalogService.findProductsByCategoryCode(categoryCode);

        assertEquals(1, responses.size());

        ProductResponseDTO response = responses.get(0);

        assertEquals(product.getId(), response.getId());
        assertEquals(product.getProductCode(), response.getProductCode());
        assertEquals(product.getProductCategory().getCategoryCode(), response.getProductCategory().getCategoryCode());
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

        assertEquals(exception.getStatusCode(), 404);
        assertEquals(exception.getMessage(), NOT_FOUND_PRODUCT);
    }

    @Test
    void shouldFindStocksByProductCodeListOneItem() {
        String username = "testeRep";
        String productCode = "PRODUCT_CODE";
        BatchStockOrder batchStockOrder = BatchStockOrder.C;

        InfoStockDTOImpl infoStock = new InfoStockDTOImpl(1, 1, null, "SECTION_CODE", "WAREHOUSE_CODE");

        when(this.stockRepository.findWithSectionAndWarehouseByProductCode(username, productCode))
            .thenReturn(Arrays.<IInfoStockDTO>asList(infoStock));

        ProductStockResponseDTO response = this.productCatalogService.findStocksByProductCode(username, productCode, batchStockOrder);
        assertEquals(productCode, response.getProductId());

        List<IInfoStockDTO> stockResponses = response.getBatchStock();
        assertEquals(1, stockResponses.size());

        IInfoStockDTO stock = stockResponses.get(0);

        assertEquals(infoStock.getBatchNumber(), stock.getBatchNumber());
        assertEquals(infoStock.getCurrentQuantity(), stock.getCurrentQuantity());
        assertEquals(infoStock.getSectionCode(), stock.getSectionCode());
        assertEquals(infoStock.getWarehouseCode(), stock.getWarehouseCode());
    }

    @Test
    void shouldFindStocksByDueDateThrowNotFoundException() {
        int days = 10;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        when(this.stockRepository.findStockWithProductDueDateUntilFutureDate(
            date.plusDays(days).format(formatter)))
            .thenReturn(List.<IBatchStockDueDateResponseDTO>of());

        ApiException exception = assertThrows(
            ApiException.class,
            () -> this.productCatalogService.findStocksByDueDate(days));

        assertEquals(exception.getStatusCode(), 404);
        assertEquals(exception.getMessage(), NOT_FOUND_PRODUCT_LIST);
    }

    @Test
    void shouldFindStocksByDueDateListOne() {
        int days = 10;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        BatchStockDueDateResponseDTOImpl batchStockDueDate = new BatchStockDueDateResponseDTOImpl(
            "BATCH_NUMBER", "PRODUCT_ID", "FS", date, 100);

        when(this.stockRepository.findStockWithProductDueDateUntilFutureDate(
            date.plusDays(days).format(formatter)))
            .thenReturn(List.<IBatchStockDueDateResponseDTO>of(batchStockDueDate));

        List<IBatchStockDueDateResponseDTO> responses = this.productCatalogService.findStocksByDueDate(days);

        assertEquals(1, responses.size());

        IBatchStockDueDateResponseDTO response = responses.get(0);

        assertEquals(batchStockDueDate.getBatchNumber(), response.getBatchNumber());
        assertEquals(batchStockDueDate.getDueDate(), response.getDueDate());
        assertEquals(batchStockDueDate.getProductId(), response.getProductId());
        assertEquals(batchStockDueDate.getQuantity(), response.getQuantity());
        assertEquals(batchStockDueDate.getProductTypeId(), response.getProductTypeId());
    }

    @Test
    void shouldFindStocksByDueDateAndProductCategoryThrowNotFoundException() {
        EProductCategory productCategory = EProductCategory.FS;
        EResultOrder resultOrder = EResultOrder.asc;

        int days = 10;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        when(this.stockRepository.findStockWithProductDueDateUntilFutureDateByProductCategory(
            date.plusDays(days).format(formatter), productCategory.name()))
            .thenReturn(List.<IBatchStockDueDateResponseDTO>of());

        ApiException exception = assertThrows(
            ApiException.class,
            () -> this.productCatalogService.findStocksByDueDateAndProductCategory(days, productCategory, resultOrder));

        assertEquals(exception.getStatusCode(), 404);
        assertEquals(exception.getMessage(), NOT_FOUND_PRODUCT_LIST);
    }

    @Test
    void shouldFindStocksByDueDateAndProductCategoryListOne() {
        EProductCategory productCategory = EProductCategory.FS;
        EResultOrder resultOrder = EResultOrder.asc;

        int days = 10;

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        BatchStockDueDateResponseDTOImpl batchStockDueDate = new BatchStockDueDateResponseDTOImpl(
            "BATCH_NUMBER", "PRODUCT_ID", productCategory.name(), date, 100);

        List<IBatchStockDueDateResponseDTO> batchStockDueDateResponseDTO = new ArrayList<>();
        batchStockDueDateResponseDTO.add(batchStockDueDate);

        when(this.stockRepository.findStockWithProductDueDateUntilFutureDateByProductCategory(
            date.plusDays(days).format(formatter), productCategory.name()))
            .thenReturn(batchStockDueDateResponseDTO);

        List<IBatchStockDueDateResponseDTO> responses = this.productCatalogService
            .findStocksByDueDateAndProductCategory(days, productCategory, resultOrder);

        assertEquals(1, responses.size());

        IBatchStockDueDateResponseDTO response = responses.get(0);

        assertEquals(batchStockDueDate.getBatchNumber(), response.getBatchNumber());
        assertEquals(batchStockDueDate.getDueDate(), response.getDueDate());
        assertEquals(batchStockDueDate.getProductId(), response.getProductId());
        assertEquals(batchStockDueDate.getQuantity(), response.getQuantity());
        assertEquals(batchStockDueDate.getProductTypeId(), response.getProductTypeId());
    }
}
