package com.mercadolibre.fresco.dtos.response.aggregetion;

import java.math.BigDecimal;

public interface IWarehouseProductCountDTO {
    String getWarehouseCode();
    BigDecimal getTotalQuantity();
}
