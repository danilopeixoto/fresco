package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.model.Product;

public interface IProductService extends ICRUD<Product> {

    Product findById(String id);
}
