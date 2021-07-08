package com.mercadolibre.fresco.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryResponseDTO {
    private Long Id;
    private String categoryCode;
}
