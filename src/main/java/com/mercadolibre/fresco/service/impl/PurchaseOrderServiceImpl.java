package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.service.IPurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    @Override
    public PurchaseOrderDTO create(String username, PurchaseOrderDTO purchaseOrderDTO) {
        return null;
    }

    @Override
    public PurchaseOrderDTO update(String username, PurchaseOrderDTO purchaseOrderDTO) {
        return null;
    }

    @Override
    public List<ProductResponseDTO> listProductsByCategory(String username, Long id) {
        return null;
    }
}
