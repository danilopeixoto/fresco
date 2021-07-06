package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.dtos.InfoStockDTO;
import com.mercadolibre.fresco.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Modifying
    @Query(value = "UPDATE stocks SET cur_quantity = :cur_quantity WHERE id = :id", nativeQuery = true)
    void updateCurrentQuantityById(@Param("id") Long id, @Param("cur_quantity") int cur_quantity);

    @Query(value = "SELECT * FROM stocks INNER JOIN products ON products.id = stocks.product_id WHERE products.product_code = :productCode", nativeQuery = true)
    List<Stock> findByProductCode(@Param("productCode") String productCode);

    @Query(value = "SELECT * from stocks WHERE product_id = :productId", nativeQuery = true)
    List<Stock> findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT * FROM stocks INNER JOIN products ON products.id = stocks.product_id WHERE products.product_code = :productCode and stocks.cur_quantity >= :quantity", nativeQuery = true)
    List<Stock> findByProductCodeWithCurrentQuantity(@Param("productCode") String productCode, @Param("quantity") Integer quantity);

    void deleteByBatchNumber(Integer batchNumber);

    @Query(value = "SELECT s.batch_number, s.cur_quantity as current_quantity, p.due_date, sc.section_code, w.warehouse_code FROM stocks s " +
            "INNER JOIN products p ON p.id = s.product_id " +
            "INNER JOIN warehouse_section ws ON s.warehouse_section_id = ws.id " +
            "INNER JOIN sections sc ON sc.id = ws.section_id " +
            "INNER JOIN warehouses w ON w.id = ws.warehouse_id " +
            "INNER JOIN user_accounts ac ON ac.id = w.agent_id " +
            "WHERE p.product_code = :productCode " +
            "and ac.username = :username", nativeQuery = true)
    List<Object[]> findWithSectionAndWarehouseByProductCode(@Param("username") String username,@Param("productCode") String productCode);

}
