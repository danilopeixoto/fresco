package com.mercadolibre.fresco.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO {

    @NotNull(message = "statusCode cannot be null.")
    private String statusCode;
}


