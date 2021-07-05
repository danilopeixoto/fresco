package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.service.IInboundOrderService;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

@Service
public class InboundOrderServiceImpl implements IInboundOrderService {
    private IWarehouseService warehouseService;

    public InboundOrderServiceImpl(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Override
    public InboundOrderResponseDTO create(InboundOrderDTO inboundOrderDTO) throws NotFoundException, BadRequestException {

        return null;
    }

    @Override
    public InboundOrderResponseDTO update(InboundOrderDTO inboundOrderDTO) throws NotFoundException, BadRequestException {
        return null;
    }
}
