package com.mercadolibre.fresco.service.crud;

import com.mercadolibre.fresco.model.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductService extends ICRUD<Product> {
  Product findByProductCode(String productCode);

  List<Product> findAll();

  List<Product> findProductsByCategoryCode(@Param("categoryCode") String categoryCode);
}
