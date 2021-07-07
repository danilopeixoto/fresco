package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.dtos.InfoStockDTO;
import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponse;
import com.mercadolibre.fresco.model.Stock;

import java.util.List;

public interface IStockService extends ICRUD<Stock> {

    Stock validStockForExistingOrder(ProductsDTO productsDTO, Long orderId);

    Stock validProductStockForPurchaseOrder(ProductsDTO productsDTO);

    List<Stock> findByProductCode(String productCode);

    Stock updateCurrentQuantityById(Long id, Integer cur_quantity);

    List<Stock> checkStockWithProductAvailability(String productCode, int quantity);

    List<InfoStockDTO> findWithSectionAndWarehouseByProductCode(String username, String productCode);

    List<IBatchStockDueDateResponse> findStockWithProductDueDateUntilFutureDate(Integer dayQuantity);

    void deleteByBatchNumber(Integer batchNumber);

    List<IBatchStockDueDateResponse> findStockWithProductDueDateUntilFutureByProductCategory(Integer dayQuantity, String productCategory);
}
