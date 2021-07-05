package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;

import java.util.List;

public interface IPurchaseOrderService {
    PurchaseOrderDTO create(String username, PurchaseOrderDTO purchaseOrderDTO);

    PurchaseOrderDTO update(String username, PurchaseOrderDTO purchaseOrderDTO);

    List<ProductResponseDTO> listProductsByCategory(String username, Long id);
}
