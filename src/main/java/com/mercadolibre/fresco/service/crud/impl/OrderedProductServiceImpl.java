package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.model.OrderedProduct;
import com.mercadolibre.fresco.repository.OrderedProductRepository;
import com.mercadolibre.fresco.service.IPurchaseOrderService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.OrderedProductService;
import org.springframework.stereotype.Service;

@Service
public class OrderedProductServiceImpl implements OrderedProductService {

    private final OrderedProductRepository orderedProductRepository;
    private final IProductService productService;
    private final IPurchaseOrderService purchaseOrderService;

    public OrderedProductServiceImpl(OrderedProductRepository orderedProductRepository,
                                     IProductService productService, IPurchaseOrderService purchaseOrderService) {
        this.orderedProductRepository = orderedProductRepository;
        this.productService = productService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public OrderedProduct updateOrderedProduct(Long orderId, ProductsDTO productsDTO) {
        OrderedProduct orderedProductToBeUpdated = this.orderedProductRepository
            .findByProductCodeAndOrderId(productsDTO.getProductId(), orderId);

        if (orderedProductToBeUpdated == null) {
            OrderedProduct orderedProduct = new OrderedProduct().toBuilder()
                .product(this.productService.findByProductCode(productsDTO.getProductId()))
                .purchaseOrder(this.purchaseOrderService.findPurchaseOrderById(orderId))
                .quantity(productsDTO.getQuantity())
                .build();
            return this.orderedProductRepository.save(orderedProduct);
        }

        orderedProductToBeUpdated.setQuantity(productsDTO.getQuantity());
        return this.orderedProductRepository.save(orderedProductToBeUpdated);
    }
}
