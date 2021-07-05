package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.service.IProductCatalogService;

import java.util.List;

public class ProductCatalogServiceImpl implements IProductCatalogService {
    @Override
    public List<ProductResponseDTO> findAll() {
        return null;
    }

    @Override
    public List<ProductResponseDTO> findProductsByCategoryCode(EProductCategory category) {
        return null;
    }
}
