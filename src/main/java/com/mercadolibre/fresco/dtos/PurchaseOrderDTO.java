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
public class PurchaseOrderDTO {
    @NotNull
    private Long id;

    @NotNull(message = "date cannot be null.")
    private LocalDate date;

    @NotNull(message = "buyerId cannot be null.")
    private String buyerId;

    @NotNull(message = "orderStatus cannot be null.")
    private OrderStatusDTO orderStatus;

    @NotNull(message = "products cannot be null.")
    private List<ProductsDTO> products;
}
