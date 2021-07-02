package com.mercadolibre.fresco.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {
    @NotNull(message = "sectionCode cannot be null.")
    public String sectionCode;

    @NotNull(message = "warehouseCode cannot be null.")
    public String warehouseCode;
}
