package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IInfoStockDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.OrderedProduct;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.repository.OrderedProductRepository;
import com.mercadolibre.fresco.repository.StockRepository;
import com.mercadolibre.fresco.service.crud.IStockService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements IStockService {

    private StockRepository stockRepository;
    private OrderedProductRepository orderedProductRepository;

    public StockServiceImpl(StockRepository stockRepository, OrderedProductRepository orderedProductRepository) {
        this.stockRepository = stockRepository;
        this.orderedProductRepository = orderedProductRepository;
    }

    @Override
    public Stock create(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock update(Stock stock) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public Stock findById(Long id) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock == null) {
            throw new ApiException("404", "Stock not found", 404);
        }
        return stock;
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) {
            throw new ApiException("404", "Stock not found", 404);
        }
        return stocks;
    }

    @Override
    public Stock validStockForExistingOrder(ProductsDTO productsDTO, Long orderId) {
        Integer actualQuantity = this.getQuantityToUpdate(orderId, productsDTO);
        List<Stock> productStocks = this.findByProductCode(productsDTO.getProductId());

        //Due date validate
        productStocks = this.validStocksByDueDate(productStocks);

        //Stock availability validate
        productStocks = this.validStockAvailability(productStocks, actualQuantity);

        if (productStocks.isEmpty()){
            throw new ApiException("404", "Products in order " + orderId + "not exists!", 404);
        }

        //Decrease amount of larger stock based on last quantity
        return this.decreaseAmountOfStock(productStocks, actualQuantity);

    }

    @Override
    public Stock validProductStockForPurchaseOrder(ProductsDTO productsDTO) {
        List<Stock> productStocks = this.findByProductCode(productsDTO.getProductId());

        //Due date validate
        productStocks = this.validStocksByDueDate(productStocks);

        //Stock availability validate
        productStocks = this.validStockAvailability(productStocks, productsDTO.getQuantity());

        if (productStocks.isEmpty()){
            throw new ApiException("404", "Products in stock not exists!", 404);
        }
        //Decrease amount of larger stock
        return this.decreaseAmountOfStock(productStocks, productsDTO.getQuantity());

    }

    @Override
    public List<Stock> findByProductCode(String productCode) {
        List<Stock> stocks = this.stockRepository.findByProductCode(productCode);

        if (stocks.isEmpty()) {
            throw new ApiException("404", "Product with code " + productCode + " not found", 404);
        }

        return stocks;
    }


    @Override
    public Stock updateCurrentQuantityById(Long id, Integer purchaseQuantity) {
        Stock stock = this.findById(id);
        if (stock == null){
            throw new ApiException("404", "Stock with id " + id +" not found!", 404);
        }
        stock.setCurrentQuantity((stock.getCurrentQuantity() - purchaseQuantity));
        return this.stockRepository.save(stock);
    }

    @Override
    public List<IInfoStockDTO> findWithSectionAndWarehouseByProductCode(String username, String productCode) {

        List<IInfoStockDTO> stocks = stockRepository.findWithSectionAndWarehouseByProductCode(username, productCode);
        if (stocks.isEmpty()) {
            throw new ApiException("404", "Products not found.", 404);
        }
        return stocks;
    }

    @Override
    public List<IBatchStockDueDateResponseDTO> findStockWithProductDueDateUntilFutureDate(Integer dayQuantity) {
        LocalDate futureTime = LocalDate.now().plusDays(dayQuantity);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<IBatchStockDueDateResponseDTO> stocks = stockRepository.findStockWithProductDueDateUntilFutureDate(futureTime.format(formatter));
        if (stocks.isEmpty()) {
            throw new ApiException("404", "Product list not found.", 404);
        }

        return stocks;
    }

    @Override
    public List<IBatchStockDueDateResponseDTO> findStockWithProductDueDateUntilFutureByProductCategory(Integer dayQuantity, String productCategory) {
        LocalDate futureTime = LocalDate.now().plusDays(dayQuantity);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<IBatchStockDueDateResponseDTO> stocks = stockRepository.findStockWithProductDueDateUntilFutureDateByProductCategory(futureTime.format(formatter), productCategory);
        if (stocks.isEmpty()) {
            throw new ApiException("404", "Products not found.", 404);
        }

        return stocks;
    }

    @Override
    public Integer countStocksOnSection(Long warehouseSectionId) {
        return stockRepository.countStocksOnSection(warehouseSectionId);
    }

    @Override
    public Stock findByBatchNumber(Integer batchNumber) {
        Stock stock = stockRepository.findByBatchNumber(batchNumber);
        if (stock == null) {
            throw new ApiException("404", "Stock not found.", 404);
        }
        return stock;
    }

    @Override
    public void deleteByBatchNumber(Integer batchNumber) {
        this.stockRepository.deleteByBatchNumber(batchNumber);
    }


    private List<Stock> validStocksByDueDate(List<Stock> stocks) {
        LocalDate futureTime = LocalDate.now().plusWeeks(3);

        stocks = stocks.stream()
            .filter(stock -> stock.getDueDate().isAfter(futureTime))
            .collect(Collectors.toList());

        if (stocks.isEmpty())
            throw new ApiException("404","Not Found Exception. Non stock of product found in valid due date", 404);

        return stocks;
    }

    private List<Stock> validStockAvailability(List<Stock> stocks, Integer quantity) {
        stocks = stocks.stream()
            .filter(stock -> stock.getCurrentQuantity() >= quantity)
            .collect(Collectors.toList());

        if (stocks.isEmpty())
            throw new ApiException("404", "Not Found Exception. Non stock of product found with availability", 404);

        return stocks;
    }

    private Stock decreaseAmountOfStock(List<Stock> stocks, Integer quantity) {
        Stock largerStock = stocks.stream()
            .max(Comparator.comparing(Stock::getCurrentQuantity)).orElse(null);

        return this.updateCurrentQuantityById(largerStock.getId(), quantity);
    }

    private Integer getQuantityToUpdate(Long orderId, ProductsDTO productsDTO) {
        OrderedProduct existingOrderedProduct = this.orderedProductRepository.findByProductCodeAndOrderId(productsDTO.getProductId(),
            orderId);
        if (existingOrderedProduct == null){
            throw new ApiException("404", "Order with id " + orderId + " not exists!", 404);
        }

        if (existingOrderedProduct != null)
            return productsDTO.getQuantity() - existingOrderedProduct.getQuantity();
        return productsDTO.getQuantity();
    }

}
