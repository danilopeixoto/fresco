package com.mercadolibre.fresco.repository;

import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IInfoStockDTO;
import com.mercadolibre.fresco.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "SELECT * FROM stocks INNER JOIN products ON products.id = stocks.product_id WHERE products.product_code = :productCode", nativeQuery = true)
    List<Stock> findByProductCode(@Param("productCode") String productCode);

    @Query(value = "SELECT * from stocks WHERE product_id = :productId", nativeQuery = true)
    List<Stock> findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT * FROM stocks INNER JOIN products ON products.id = stocks.product_id WHERE products.product_code = :productCode and stocks.cur_quantity >= :quantity", nativeQuery = true)
    List<Stock> findByProductCodeWithCurrentQuantity(@Param("productCode") String productCode, @Param("quantity") Integer quantity);

    void deleteByBatchNumber(Integer batchNumber);

    @Query(value = "SELECT s.batch_number as batchNumber, s.cur_quantity as currentQuantity, s.due_date as dueDate, sc.section_code as sectionCode, w.warehouse_code as warehouseCode FROM stocks s " +
        "INNER JOIN products p ON p.id = s.product_id " +
        "INNER JOIN warehouse_section ws ON s.warehouse_section_id = ws.id " +
        "INNER JOIN sections sc ON sc.id = ws.section_id " +
        "INNER JOIN warehouses w ON w.id = ws.warehouse_id " +
        "INNER JOIN user_accounts ac ON ac.id = w.agent_id " +
        "WHERE p.product_code = :productCode " +
        "and ac.username = :username", nativeQuery = true)
    List<IInfoStockDTO> findWithSectionAndWarehouseByProductCode(@Param("username") String username, @Param("productCode") String productCode);

    @Query(value = "SELECT s.batch_number as batchNumber, p.product_code as productId, pc.category_code as productTypeId, s.due_date as dueDate, s.cur_quantity as quantity FROM stocks as s " +
        "INNER JOIN products as p ON p.id = s.product_id " +
        "INNER JOIN product_categories as pc ON pc.id = p.product_category_id " +
        "WHERE s.due_date <= :futureDate ", nativeQuery = true)
    List<IBatchStockDueDateResponseDTO> findStockWithProductDueDateUntilFutureDate(@Param("futureDate") String futureDate);

    @Query(value = "SELECT s.batch_number as batchNumber, p.product_code as productId, pc.category_code as productTypeId, s.due_date as dueDate, s.cur_quantity as quantity FROM stocks as s " +
        "INNER JOIN products as p ON p.id = s.product_id " +
        "INNER JOIN product_categories as pc ON pc.id = p.product_category_id " +
        "WHERE s.due_date <= :futureDate and pc.category_code = :productCategory ", nativeQuery = true)
    List<IBatchStockDueDateResponseDTO> findStockWithProductDueDateUntilFutureDateByProductCategory(@Param("futureDate") String futureDate, @Param("productCategory") String productCategory);

    @Query(value = "SELECT count(warehouse_section_id) FROM stocks WHERE warehouse_section_id = :warehouseSectionId and cur_quantity > 0", nativeQuery = true)
    Integer countStocksOnSection(@Param("warehouseSectionId") Long warehouseSectionId);

    @Query(value = "SELECT * from stocks WHERE batch_number = :batchNumber", nativeQuery = true)
    Stock findByBatchNumber(@Param("batchNumber") Integer batchNumber);
}
