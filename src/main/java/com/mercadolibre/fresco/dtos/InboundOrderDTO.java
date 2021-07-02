package com.mercadolibre.fresco.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrderDTO {
    @NotNull(message = "orderNumber cannot be null.")
    public Long orderNumber;

    @NotNull(message = "orderDate cannot be null.")
    public LocalDate orderDate;

    @NotNull(message = "section cannot be null.")
    public SectionDTO section;

    @NotNull(message = "batchStockDTO cannot be null.")
    public List<StockDTO> batchStock;
}
