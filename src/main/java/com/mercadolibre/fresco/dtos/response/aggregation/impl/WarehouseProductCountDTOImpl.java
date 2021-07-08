package com.mercadolibre.fresco.dtos.response.aggregation.impl;

import com.mercadolibre.fresco.dtos.response.aggregation.IWarehouseProductCountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProductCountDTOImpl implements IWarehouseProductCountDTO {
    public String warehouseCode;
    public BigDecimal totalQuantity;
}
