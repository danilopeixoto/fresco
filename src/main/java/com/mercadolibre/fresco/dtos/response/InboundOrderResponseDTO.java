package com.mercadolibre.fresco.dtos.response;

import com.mercadolibre.fresco.dtos.StockDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Builder(toBuilder = true)
@Data
public class InboundOrderResponseDTO {
    public List<StockDTO> batchStock;
}
