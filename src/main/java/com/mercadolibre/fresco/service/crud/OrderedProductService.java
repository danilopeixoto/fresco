package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.model.OrderedProduct;

public interface OrderedProductService {

    OrderedProduct updateOrderedProduct(Long orderId, ProductsDTO productsDTO);

}
