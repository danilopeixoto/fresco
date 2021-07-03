package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductService extends ICRUD<ProductResponseDTO> {
    ProductResponseDTO findByProductCode(String productCode);

    List<ProductResponseDTO> findAll();
    List<ProductResponseDTO> findProductsByCategoryCode(@Param("categoryCode") String categoryCode);
}
