package com.mercadolibre.fresco.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {
    @NotNull
    public String sectionCode;

    @NotNull
    public String warehouseCode;
}
