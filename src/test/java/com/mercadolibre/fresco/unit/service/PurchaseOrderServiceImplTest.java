package com.mercadolibre.fresco.unit.service;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.model.OrderedProduct;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.model.ProductCategory;
import com.mercadolibre.fresco.model.PurchaseOrder;
import com.mercadolibre.fresco.model.enumeration.StatusCode;
import com.mercadolibre.fresco.repository.ProductRepository;
import com.mercadolibre.fresco.repository.PurchaseOrderRepository;
import com.mercadolibre.fresco.repository.UserRepository;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.OrderedProductService;
import com.mercadolibre.fresco.service.crud.impl.ProductServiceImpl;
import com.mercadolibre.fresco.service.impl.PurchaseOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PurchaseOrderServiceImplTest {

    PurchaseOrderServiceImpl purchaseOrderService;
    IProductService productService;
    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    PurchaseOrderRepository purchaseOrderRepository = Mockito.mock(PurchaseOrderRepository.class);
    OrderedProductService orderedProductService;

    @BeforeEach
    public void setup() {
        this.productService = new ProductServiceImpl(productRepository);
        this.purchaseOrderService = new PurchaseOrderServiceImpl(productService, userRepository,
            purchaseOrderRepository, orderedProductService, null);
    }

    @Test
    void shouldGetProductsByOrderId() {
        PurchaseOrder purchaseOrder = new PurchaseOrder(1L, StatusCode.PENDENTE, LocalDate.now(), null, null);
        OrderedProduct banana = new OrderedProduct().toBuilder()
            .product(new Product(1L,"BANANA", null, 5., null, null, null))
            .purchaseOrder(purchaseOrder)
            .quantity(10).build();
        List<OrderedProduct> orderedProductList = new ArrayList<>();
        orderedProductList.add(banana);
        purchaseOrder.setOrderedProducts(orderedProductList);

        when(this.purchaseOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(purchaseOrder));
        List<ProductsDTO> products = this.purchaseOrderService.getProductsByOrderId(1L);
        assertEquals("BANANA", products.get(0).getProductId());
        assertEquals(10, products.get(0).getQuantity());
    }

    @Test
    void shouldFindPurchaseOrderById() {
        PurchaseOrder purchaseOrder = new PurchaseOrder(1L, StatusCode.PENDENTE, LocalDate.now(), null, null);
        when(purchaseOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(purchaseOrder));
        assertEquals("Pending", purchaseOrder.getStatusCode().getStatus());
    }


}
