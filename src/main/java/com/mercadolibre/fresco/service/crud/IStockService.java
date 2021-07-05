package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.model.Stock;

import java.util.List;

public interface IStockService extends ICRUD<Stock> {

    public Stock updateCurrentQuantityById(Long id, Integer cur_quantity);

    public List<Stock> checkStockWithProductAvailability(String productCode, int quantity);

}
