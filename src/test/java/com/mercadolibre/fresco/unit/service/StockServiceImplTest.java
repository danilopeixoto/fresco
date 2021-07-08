package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.OrderedProduct;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.model.PurchaseOrder;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.repository.OrderedProductRepository;
import com.mercadolibre.fresco.repository.StockRepository;
import com.mercadolibre.fresco.service.crud.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class StockServiceImplTest {

    static final String NOT_FOUND_AVAILABILITY_MESSAGE = "Not Found Exception. Non stock of product found with availability";
    static final String NOT_FOUND_DUE_DATE_MESSAGE = "Not Found Exception. Non stock of product found in valid due date";

    static List<OrderedProduct> orderedProducts;
    static OrderedProduct orderedProduct;
    static PurchaseOrder purchaseOrder;
    static Stock stock;
    static Product banana;
    static List<Stock> stocks;
    static ProductsDTO bananaDto;

    StockServiceImpl stockService;
    StockRepository stockRepository = Mockito.mock(StockRepository.class);
    OrderedProductRepository orderedProductRepository = Mockito.mock(OrderedProductRepository.class);

    @BeforeEach
    void setup() {
        stockService = new StockServiceImpl(stockRepository, orderedProductRepository);
    }


    @Test
    void shouldValidFirstTimePurchaseOrder() {
        banana = new Product();
        banana.setId(1L);
        banana.setProductCode("BANANA");
        banana.setDueDate(LocalDate.now().plusWeeks(8));
        stock = new Stock().toBuilder().batchNumber(1).id(1L).currentQuantity(50).initialQuantity(50)
            .currentTemperature(10.).product(banana).build();
        stocks = new ArrayList<>();
        stocks.add(stock);
        bananaDto = new ProductsDTO().toBuilder().productId("BANANA").quantity(10).build();

        when(this.stockRepository.findByProductCode("BANANA")).thenReturn(stocks);
        when(this.stockRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(stock));
        when(this.stockRepository.save(stock)).thenReturn(stock);

        Stock stockAfterOrder = this.stockService.validProductStockForPurchaseOrder(bananaDto);
        assertEquals(40, stockAfterOrder.getCurrentQuantity());
    }

    @Test
    void shouldFailInDueDate() {
        banana = new Product();
        banana.setId(1L);
        banana.setProductCode("BANANA");
        banana.setDueDate(LocalDate.now().plusWeeks(1));
        stock = new Stock().toBuilder().batchNumber(1).id(1L).currentQuantity(50).initialQuantity(50)
            .currentTemperature(10.).product(banana).build();
        stocks = new ArrayList<>();
        stocks.add(stock);
        bananaDto = new ProductsDTO().toBuilder().productId("BANANA").quantity(10).build();

        when(this.stockRepository.findByProductCode("BANANA")).thenReturn(stocks);
        when(this.stockRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(stock));
        when(this.stockRepository.save(stock)).thenReturn(stock);

        ApiException e = assertThrows(ApiException.class, () -> this.stockService.validProductStockForPurchaseOrder(bananaDto));
        assertEquals(NOT_FOUND_DUE_DATE_MESSAGE, e.getMessage());
    }

    @Test
    void shouldFailInAvailability() {
        banana = new Product();
        banana.setId(1L);
        banana.setProductCode("BANANA");
        banana.setDueDate(LocalDate.now().plusWeeks(10));
        stock = new Stock().toBuilder().batchNumber(1).id(1L).currentQuantity(50).initialQuantity(50)
            .currentTemperature(10.).product(banana).build();
        stocks = new ArrayList<>();
        stocks.add(stock);
        bananaDto = new ProductsDTO().toBuilder().productId("BANANA").quantity(100).build();

        when(this.stockRepository.findByProductCode("BANANA")).thenReturn(stocks);
        when(this.stockRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(stock));
        when(this.stockRepository.save(stock)).thenReturn(stock);

        ApiException e = assertThrows(ApiException.class, () -> this.stockService.validProductStockForPurchaseOrder(bananaDto));
        assertEquals(NOT_FOUND_AVAILABILITY_MESSAGE, e.getMessage());
    }

    @Test
    void shouldValidExistingOrder() {
        banana = new Product();
        banana.setId(1L);
        banana.setProductCode("BANANA");
        banana.setDueDate(LocalDate.now().plusWeeks(8));
        stock = new Stock().toBuilder().batchNumber(1).id(1L).currentQuantity(45).initialQuantity(50)
            .currentTemperature(10.).product(banana).build();
        stocks = new ArrayList<>();
        stocks.add(stock);
        bananaDto = new ProductsDTO().toBuilder().productId("BANANA").quantity(50).build();
        orderedProduct = new OrderedProduct().toBuilder().product(banana).quantity(5).build();
        orderedProducts = new ArrayList<>();
        orderedProducts.add(orderedProduct);
        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderedProducts(orderedProducts);
        purchaseOrder.setId(1L);

        when(this.orderedProductRepository.findByProductCodeAndOrderId("BANANA",1L)).thenReturn(orderedProduct);
        when(this.stockRepository.findByProductCode("BANANA")).thenReturn(stocks);
        when(this.stockRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(stock));
        when(this.stockRepository.save(stock)).thenReturn(stock);

        Stock stockAfterOrder = this.stockService.validStockForExistingOrder(bananaDto, 1L);
        assertEquals(0, stockAfterOrder.getCurrentQuantity());
    }

    @Test
    void shouldFailInAvailabilityForExistingOrder() {
        banana = new Product();
        banana.setId(1L);
        banana.setProductCode("BANANA");
        banana.setDueDate(LocalDate.now().plusWeeks(8));
        stock = new Stock().toBuilder().batchNumber(1).id(1L).currentQuantity(45).initialQuantity(50)
            .currentTemperature(10.).product(banana).build();
        stocks = new ArrayList<>();
        stocks.add(stock);
        bananaDto = new ProductsDTO().toBuilder().productId("BANANA").quantity(51).build();
        orderedProduct = new OrderedProduct().toBuilder().product(banana).quantity(5).build();
        orderedProducts = new ArrayList<>();
        orderedProducts.add(orderedProduct);
        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderedProducts(orderedProducts);
        purchaseOrder.setId(1L);

        when(this.orderedProductRepository.findByProductCodeAndOrderId("BANANA",1L)).thenReturn(orderedProduct);
        when(this.stockRepository.findByProductCode("BANANA")).thenReturn(stocks);
        when(this.stockRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(stock));
        when(this.stockRepository.save(stock)).thenReturn(stock);

        ApiException e = assertThrows(ApiException.class, () -> this.stockService.validStockForExistingOrder(bananaDto, 1L));
        assertEquals(NOT_FOUND_AVAILABILITY_MESSAGE, e.getMessage());
    }



}
