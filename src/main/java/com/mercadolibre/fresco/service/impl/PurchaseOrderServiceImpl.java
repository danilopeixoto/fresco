package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.OrderedProduct;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.model.PurchaseOrder;
import com.mercadolibre.fresco.model.enumeration.StatusCode;
import com.mercadolibre.fresco.repository.PurchaseOrderRepository;
import com.mercadolibre.fresco.repository.UserRepository;
import com.mercadolibre.fresco.service.IPurchaseOrderService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IStockService;
import com.mercadolibre.fresco.service.crud.IUserService;
import com.mercadolibre.fresco.service.crud.OrderedProductService;
import com.mercadolibre.fresco.service.crud.impl.ProductServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    private final IProductService productService;
    private final UserRepository userRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderedProductService orderedProductService;
    private final IStockService stockService;

    public PurchaseOrderServiceImpl(IProductService productService, UserRepository userRepository, PurchaseOrderRepository purchaseOrderRepository,
                                    @Lazy OrderedProductService orderedProductService, IStockService stockService) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.orderedProductService = orderedProductService;
        this.stockService = stockService;
    }

    @Override
    public List<ProductsDTO> getProductsByOrderId(Long id) {
        List<ProductsDTO> productsDTOS = new ArrayList<>();
        PurchaseOrder purchaseOrder = this.findPurchaseOrderById(id);
        purchaseOrder.getOrderedProducts().forEach(
                orderedProduct -> productsDTOS.add(new ProductsDTO()
                        .toBuilder()
                        .productId(orderedProduct.getProduct().getProductCode())
                        .quantity(orderedProduct.getQuantity()).build())
        );

        return productsDTOS;
    }

    @Override
    public PurchaseOrder findPurchaseOrderById(Long id) {
         PurchaseOrder purchaseOrder = this.purchaseOrderRepository.findById(id)
                .orElse(null);
         if(purchaseOrder == null)
             throw new NotFoundException("Cannot find Purchase Order with ID:" + id);
         return purchaseOrder;
    }

    @Override
    public PurchaseOrderResponseDTO create(PurchaseOrderDTO purchaseOrderDTO) {
        purchaseOrderDTO.getProducts().forEach(
                this::validPurchaseOrder
        );

        PurchaseOrder purchaseOrder = this.buildPurchaseOrder(purchaseOrderDTO);
        this.purchaseOrderRepository.save(purchaseOrder);

        return new PurchaseOrderResponseDTO().toBuilder()
                .totalPrice(this.getTotalPrice(purchaseOrderDTO.getProducts())).build();
    }

    @Override
    public PurchaseOrderResponseDTO update(PurchaseOrderDTO purchaseOrderDto) {
        PurchaseOrder purchaseOrderToBeUpdated = this.findPurchaseOrderById(purchaseOrderDto.getId());

        purchaseOrderDto.getProducts().forEach(
                productsDTO -> this.validUpdatedPurchaseOrder(productsDTO, purchaseOrderDto.getId())
        );

        this.buildUpdatedPurchaseOrder(purchaseOrderToBeUpdated, purchaseOrderDto);
        this.purchaseOrderRepository.save(purchaseOrderToBeUpdated);

        return new PurchaseOrderResponseDTO().toBuilder()
                .totalPrice(this.getTotalPrice(purchaseOrderDto.getProducts())).build();
    }

    @Override
    public List<ProductResponseDTO> listProductsByCategory(String username, Long id) {
        return null;
    }

    private PurchaseOrder buildUpdatedPurchaseOrder(PurchaseOrder purchaseOrderToBeUpdated,
                                                    PurchaseOrderDTO purchaseOrderDTO) {
        purchaseOrderDTO.getProducts().forEach(
                productsDTO -> this.orderedProductService.updateOrderedProduct(purchaseOrderToBeUpdated.getId(),
                        productsDTO)
        );

        purchaseOrderToBeUpdated.setStatusCode(StatusCode.valueOf(purchaseOrderDTO.getStatusCode()));

        return purchaseOrderToBeUpdated;
    }

    private PurchaseOrder buildPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
        List<OrderedProduct> orderedProducts = new ArrayList<>();


        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrderDTO.getProducts().forEach(
                productsDTO -> orderedProducts.add(new OrderedProduct().toBuilder()
                        .purchaseOrder(purchaseOrder)
                        .product(this.productService.findByProductCode(productsDTO.getProductId()))
                        .quantity(productsDTO.getQuantity()).build())
        );
        purchaseOrder.setOrderedProducts(orderedProducts);
        purchaseOrder.setStatusCode(StatusCode.valueOf(purchaseOrderDTO.getStatusCode()));
        purchaseOrder.setDate(purchaseOrderDTO.getDate());
        purchaseOrder.setUser(this.userRepository.findByUsername(purchaseOrderDTO.getBuyerId()));

        return purchaseOrder;
    }

    private Double getTotalPrice(List<ProductsDTO> productsDTOS) {
        Double totalValue = 0.;

        for(ProductsDTO productsDTO : productsDTOS) {
            totalValue += this.productService.findByProductCode(productsDTO.getProductId()).getPrice() * productsDTO.getQuantity();
        }

        return totalValue;
    }

    private void validPurchaseOrder(ProductsDTO productsDTO) {
        if(this.stockService.validProductStockForPurchaseOrder(productsDTO) == null)
            throw new ApiException("404", "Unavailable quantity in stock for product: " + productsDTO.getProductId(), 404);
    }

    private void validUpdatedPurchaseOrder(ProductsDTO productsDTO, Long orderId) {
        if(this.stockService.validStockForExistingOrder(productsDTO, orderId) == null)
            throw new ApiException("404", "Unavailable quantity in stock for product: " + productsDTO.getProductId(), 404);
    }

}
