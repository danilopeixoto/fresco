package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.PurchaseOrderResponseDTO;
import com.mercadolibre.fresco.model.PurchaseOrder;

import java.util.List;

public interface IPurchaseOrderService {

    PurchaseOrder findPurchaseOrderById(Long id);

    PurchaseOrderResponseDTO create(PurchaseOrderDTO purchaseOrderDTO);

    PurchaseOrderResponseDTO update(PurchaseOrderDTO purchaseOrderDto);

    List<ProductsDTO> getProductsByOrderId(Long id);

    List<ProductResponseDTO> listProductsByCategory(String username, Long id);
}
