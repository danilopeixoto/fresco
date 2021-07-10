package com.mercadolibre.fresco.dtos.response;

import com.mercadolibre.fresco.dtos.response.aggregation.IWarehouseProductCountDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehousesProductCountResponseDTO {
    private String productId;
    private List<IWarehouseProductCountDTO> warehouses;
}
