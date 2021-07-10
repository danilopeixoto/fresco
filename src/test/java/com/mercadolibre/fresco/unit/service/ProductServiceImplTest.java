package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.model.ProductCategory;
import com.mercadolibre.fresco.repository.ProductRepository;
import com.mercadolibre.fresco.service.crud.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        this.productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void shouldFindByProductCode() {
        Product banana = new Product(1L, "BANANA", null, 5., null, null, null);
        when(productRepository.findByProductCode("BANANA")).thenReturn(banana);
        Product testProduct = productService.findByProductCode("BANANA");
        assertEquals(1L, testProduct.getId());
        assertEquals("BANANA", testProduct.getProductCode());
    }

    @Test
    void shouldFindByCategoryCode() {
        Product banana = new Product(1L, "BANANA", null, 5., null, null,
            new ProductCategory(1L, "FS", "Fresh", null, null));
        when(productRepository.findByProductCode("BANANA")).thenReturn(banana);
        Product testProduct = productService.findByProductCode("BANANA");
        assertEquals(1L, testProduct.getId());
        assertEquals("BANANA", testProduct.getProductCode());
        assertEquals("FS", testProduct.getProductCategory().getCategoryCode());
    }

}
