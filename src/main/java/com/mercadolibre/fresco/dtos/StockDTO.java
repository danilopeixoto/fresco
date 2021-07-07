package com.mercadolibre.fresco.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    @NotNull(message = "batchNumber cannot be null.")
    public Integer batchNumber;

    @NotNull(message = "productCode cannot be null.")
    public String productCode;

    @NotNull(message = "currentTemperature cannot be null.")
    public Double currentTemperature;

    @NotNull(message = "minimumTemperature cannot be null.")
    public Double minimumTemperature;

    @NotNull(message = "initialQuantity cannot be null.")
    @Min(value = 0, message = "initialQuantity cannot be less than 0.")
    public Integer initialQuantity;

    @NotNull(message = "currentQuantity cannot be null.")
    @Min(value = 0, message = "currentQuantity cannot be less than 0.")
    public Integer currentQuantity;

    @NotNull(message = "price cannot be null.")
    @Positive(message = "Price cannot be less than 0.")
    public Double price;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    @NotNull(message = "manufacturingDate cannot be null.")
    @PastOrPresent(message = "manufacturingDate cannot be a future date.")
    public LocalDate manufacturingDate;


    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonFormat(pattern = "HH:mm:ss")
    @NotNull(message = "manufacturingTime cannot be null.")
    public LocalTime manufacturingTime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    @NotNull(message = "dueDate cannot be null.")
    @Future(message = "dueDate cannot be on a past date.")
    public LocalDate dueDate;
}
