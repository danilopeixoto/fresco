package com.mercadolibre.fresco.dtos.response;

import com.mercadolibre.fresco.dtos.StockDTO;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
public class OrderResponseDTO {
    public List<StockDTO> batchStock;
}
