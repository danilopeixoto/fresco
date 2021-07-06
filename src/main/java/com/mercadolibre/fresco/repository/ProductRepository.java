package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE product_code = :productCode", nativeQuery = true)
    Product findByProductCode(@Param("productCode") String productCode);

    @Query(value = "SELECT p.* FROM products p " +
        "INNER JOIN product_categories pc on pc.id = p.product_category_id " +
        "WHERE pc.category_code = :categoryCode;", nativeQuery = true)
    List<Product> findByProductCategory(@Param("categoryCode") String categoryCode);
}
