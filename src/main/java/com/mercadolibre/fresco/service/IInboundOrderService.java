package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.InboundOrderDTO;
import com.mercadolibre.fresco.dtos.response.InboundOrderResponseDTO;

public interface IInboundOrderService {
  InboundOrderResponseDTO create(String username, InboundOrderDTO inboundOrderDTO);

  InboundOrderResponseDTO update(String username, InboundOrderDTO inboundOrderDTO);
}
