package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.service.IInboundOrderService;
import com.mercadolibre.fresco.service.IPurchaseOrderService;
import org.springframework.stereotype.Service;

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
}
