package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {

    @Query(value = "SELECT * FROM ordered_products op WHERE op.product_code = :productCode" +
        " AND op.purchase_order_id = :orderId", nativeQuery = true)
    OrderedProduct findByProductCodeAndOrderId(@Param("productCode") String productCode, @Param("orderId") Long orderId);

}
