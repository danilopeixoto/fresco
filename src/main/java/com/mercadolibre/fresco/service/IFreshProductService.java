package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.OrderDTO;
import com.mercadolibre.fresco.dtos.response.OrderResponseDTO;

public interface IFreshProductService {
    OrderResponseDTO createOrder(String token, OrderDTO orderDTO);

    OrderResponseDTO updateOrder(String token, OrderDTO orderDTO);
}
