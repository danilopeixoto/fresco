package com.mercadolibre.fresco.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {
    @NotNull(message = "productId cannot be null.")
    private String productId;
    @NotNull(message = "quantity cannot be null.")
    @Min(value = 0, message = "quantity cannot be less than 0.")
    private Integer quantity;
}
