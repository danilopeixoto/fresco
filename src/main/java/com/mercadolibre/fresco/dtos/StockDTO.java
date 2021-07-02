package com.mercadolibre.fresco.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    @NotNull(message = "batchNumber cannot be null.")
    public Integer batchNumber;

    @NotNull(message = "productId cannot be null.")
    public String productCode;

    @NotNull(message = "currentTemperature cannot be null.")
    public Float currentTemperature;

    @NotNull(message = "minimumTemperature cannot be null.")
    public Float minimumTemperature;

    @NotNull(message = "initialQuantity cannot be null.")
    @Min(value = 0, message = "initialQuantity cannot be less than 0.")
    public Integer initialQuantity;

    @NotNull(message = "currentQuantity cannot be null.")
    @Min(value = 0, message = "currentQuantity cannot be less than 0.")
    public Integer currentQuantity;

    @NotNull(message = "price cannot be null.")
    @Positive(message = "currentQuantity cannot be less than 0.")
    public Float price;

    @NotNull(message = "manufacturingDate cannot be null.")
    @PastOrPresent(message = "manufacturingDate cannot be a future date.")
    public LocalDate manufacturingDate;

    @NotNull(message = "manufacturingTime cannot be null.")
    public LocalTime manufacturingTime;

    @NotNull(message = "dueDate cannot be null.")
    @Future(message = "dueDate cannot be on a past date.")
    public LocalDate dueDate;
}
