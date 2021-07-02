package com.mercadolibre.fresco.dtos.response;

import com.mercadolibre.fresco.model.OrderedProduct;
import com.mercadolibre.fresco.model.ProductCategory;
import com.mercadolibre.fresco.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String productCode;
    private Float minimumTemperature;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;
    private Float price;
}
