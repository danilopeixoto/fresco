package com.mercadolibre.fresco.service;

import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductStockResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.model.enumeration.BatchStockOrder;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.model.enumeration.EResultOrder;

import java.util.List;

public interface IProductCatalogService {
    List<ProductResponseDTO> findAll();

    List<ProductResponseDTO> findProductsByCategoryCode(EProductCategory category);

    ProductStockResponseDTO findStocksByProductCode(String username, String productCode, BatchStockOrder order);

    List<IBatchStockDueDateResponseDTO> findStocksByDueDate(Integer dayQuantity);

    List<IBatchStockDueDateResponseDTO>  findStocksByDueDateAndProductCategory(Integer dayQuantity, EProductCategory productCategory, EResultOrder order);
}
