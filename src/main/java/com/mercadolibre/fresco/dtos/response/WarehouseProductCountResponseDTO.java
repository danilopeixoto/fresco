package com.mercadolibre.fresco.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProductCountResponseDTO {
    private String warehouseCode;
    private Integer totalQuantity;
}
