package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.dtos.InfoStockDTO;
import com.mercadolibre.fresco.model.Stock;

import javax.persistence.Tuple;
import java.util.List;

public interface IStockService extends ICRUD<Stock> {


    List<Stock> findByProductCode(String productCode);

    Stock updateCurrentQuantityById(Long id, Integer cur_quantity);

    List<Stock> checkStockWithProductAvailability(String productCode, int quantity);

    List<InfoStockDTO> findWithSectionAndWarehouseByProductCode(String username,String productCode);

    void deleteByBatchNumber(Integer batchNumber);


}
