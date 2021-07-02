package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.model.Stock;

public interface IStockService extends ICRUD<Stock> {

    Stock findById(String id);
}
