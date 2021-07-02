package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE product_code = :productCode", nativeQuery = true)
    public Product findByProductCode(@Param("productCode") String productCode);

}
