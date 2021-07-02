package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.OrderDTO;
import com.mercadolibre.fresco.dtos.response.OrderResponseDTO;
import com.mercadolibre.fresco.service.IFreshProductService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class FreshProductServiceImpl implements IFreshProductService {

    @Override
    public OrderResponseDTO createOrder(String token, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderResponseDTO updateOrder(String token, OrderDTO orderDTO) {
        return null;
    }
}
