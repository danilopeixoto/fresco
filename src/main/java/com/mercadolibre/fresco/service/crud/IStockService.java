package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IInfoStockDTO;
import com.mercadolibre.fresco.model.Stock;

import java.util.List;

public interface IStockService extends ICRUD<Stock> {

    Stock validStockForExistingOrder(ProductsDTO productsDTO, Long orderId);

    Stock validProductStockForPurchaseOrder(ProductsDTO productsDTO);

    List<Stock> findByProductCode(String productCode);

    Stock updateCurrentQuantityById(Long id, Integer cur_quantity);

    List<IInfoStockDTO> findWithSectionAndWarehouseByProductCode(String username, String productCode);

    List<IBatchStockDueDateResponseDTO> findStockWithProductDueDateUntilFutureDate(Integer dayQuantity);

    void deleteByBatchNumber(Integer batchNumber);

    List<IBatchStockDueDateResponseDTO> findStockWithProductDueDateUntilFutureByProductCategory(Integer dayQuantity, String productCategory);

    Integer countStocksOnSection(Long warehouseSectionId);

    Stock findByBatchNumber(Integer batchNumber);
}
