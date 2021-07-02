package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {



}
