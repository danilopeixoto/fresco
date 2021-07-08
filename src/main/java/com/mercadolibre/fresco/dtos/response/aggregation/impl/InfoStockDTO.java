package com.mercadolibre.fresco.dtos.response.aggregation.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mercadolibre.fresco.dtos.response.aggregation.IInfoStockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoStockDTO implements IInfoStockDTO {
    public Integer batchNumber;
    public Integer currentQuantity;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    public LocalDate dueDate;
    public String sectionCode;
    public String warehouseCode;
}
