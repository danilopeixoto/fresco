package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.PurchaseOrderDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;

public interface IPurchaseOrderService {
    PurchaseOrderDTO create(String username, PurchaseOrderDTO purchaseOrderDTO);

    PurchaseOrderDTO update(String username, PurchaseOrderDTO purchaseOrderDTO);
}
