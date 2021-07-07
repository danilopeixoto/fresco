package com.mercadolibre.fresco.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrderDTO {
    @NotNull(message = "orderNumber cannot be null.")
    public Long orderNumber;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "MM-dd-yyyy")
    @NotNull(message = "orderDate cannot be null.")
    public LocalDate orderDate;

    @NotNull(message = "section cannot be null.")
    public SectionDTO section;

    @NotNull(message = "batchStockDTO cannot be null.")
    public List<StockDTO> batchStock;
}
