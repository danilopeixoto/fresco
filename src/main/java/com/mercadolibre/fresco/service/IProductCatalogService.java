package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;

import java.util.List;

public interface IProductCatalogService {
    List<ProductResponseDTO> findAll();

    List<ProductResponseDTO> findProductsByCategoryCode(EProductCategory category);
}
