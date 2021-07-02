package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Transactional
    @Query(value = "UPDATE stocks SET cur_quantity = :cur_quantity WHERE id = :id", nativeQuery = true)
    Stock updateCurrentQuantityById(@Param("id") Long id, @Param("cur_quantity")  int cur_quantity);

    @Query(value = "SELECT * FROM stocks INNER JOIN products ON products.id = stocks.product_id WHERE products.product_code = :productCode", nativeQuery = true)
    List<Stock> findByProductCode(@Param("productCode") String productCode);

    @Query(value = "SELECT * from stocks WHERE product_id = :productId", nativeQuery = true)
    List<Stock> findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT * FROM stocks INNER JOIN products ON products.id = stocks.product_id WHERE products.product_code = :productCode and stocks.cur_quantity >= :quantity", nativeQuery = true)
    List<Stock> findByProductCodeWithCurrentQuantity(@Param("productCode") String productCode, @Param("quantity") Integer quantity);

}
