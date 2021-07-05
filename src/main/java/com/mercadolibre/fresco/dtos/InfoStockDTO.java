package com.mercadolibre.fresco.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class InfoStockDTO {
    private int batchNumber;
    private int currentQuantity;
    private LocalDate dueData;
    private String sectionCode;
    private String warehouseCode;
}
