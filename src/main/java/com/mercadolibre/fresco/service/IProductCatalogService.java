package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.InfoStockDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductStockResponseDTO;
import com.mercadolibre.fresco.model.enumeration.BatchStockOrder;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;

import java.util.List;

public interface IProductCatalogService {
    List<ProductResponseDTO> findAll();

    List<ProductResponseDTO> findProductsByCategoryCode(EProductCategory category);

    ProductStockResponseDTO findStocksByProductCode(String username, String productCode, BatchStockOrder order);
}
