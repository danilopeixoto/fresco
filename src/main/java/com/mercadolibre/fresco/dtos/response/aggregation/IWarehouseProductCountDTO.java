package com.mercadolibre.fresco.dtos.response.aggregation;

import java.math.BigDecimal;

public interface IWarehouseProductCountDTO {
    String getWarehouseCode();

    BigDecimal getTotalQuantity();
}
