package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.dtos.InfoStockDTO;
import com.mercadolibre.fresco.dtos.ProductsDTO;
import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.OrderedProduct;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.repository.OrderedProductRepository;
import com.mercadolibre.fresco.repository.StockRepository;
import com.mercadolibre.fresco.service.crud.IStockService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

        //Decrease amount of larger stock based on last quantity
        if (productStocks.size() != 0)
            return this.decreaseAmountOfStock(productStocks, actualQuantity);
        return null;
    }

    @Override
    public Stock validProductStockForPurchaseOrder(ProductsDTO productsDTO) {
        List<Stock> productStocks = this.findByProductCode(productsDTO.getProductId());

        //Due date validate
        productStocks = this.validStocksByDueDate(productStocks);

        //Stock availability validate
        productStocks = this.validStockAvailability(productStocks, productsDTO.getQuantity());

        //Decrease amount of larger stock
        if (productStocks.size() != 0)
            return this.decreaseAmountOfStock(productStocks, productsDTO.getQuantity());
        return null;
    }

    @Override
    public List<Stock> findByProductCode(String productCode) {
        List<Stock> stocks = this.stockRepository.findByProductCode(productCode);

        if (stocks.isEmpty()) {
            throw new ApiException("404", "Product not found", 404);
        }

        return stocks;
    }


    @Override
    public Stock updateCurrentQuantityById(Long id, Integer purchaseQuantity) {
        Stock stock = this.findById(id);
        stock.setCurrentQuantity((stock.getCurrentQuantity() - purchaseQuantity));
        return this.stockRepository.save(stock);
    }

    @Override
    public List<InfoStockDTO> findWithSectionAndWarehouseByProductCode(String username, String productCode) {
        List<Object[]> stocks = stockRepository.findWithSectionAndWarehouseByProductCode(username, productCode);
        if (stocks.isEmpty()) {
            throw new ApiException("404", "Product not found.", 404);
        }

        List<InfoStockDTO> infoStockDTOS = new ArrayList<>();

        stocks.forEach(stock -> infoStockDTOS.add(new InfoStockDTO((Integer) stock[0],
            (Integer) stock[1],
            LocalDate.parse(stock[2].toString()),
            stock[3].toString(),
            stock[4].toString()))
        );

        return infoStockDTOS;
    }

    @Override
    public void deleteByBatchNumber(Integer batchNumber) {
        this.stockRepository.deleteByBatchNumber(batchNumber);
    }

    private List<Stock> validStocksByDueDate(List<Stock> stocks) {
        LocalDate futureTime = LocalDate.now().plusWeeks(3);

        stocks = stocks.stream()
            .filter(stock -> stock.getProduct().getDueDate().isAfter(futureTime))
            .collect(Collectors.toList());

        if (stocks.size() == 0)
            throw new NotFoundException("Non stock of product found in valid due date");

        return stocks;
    }

    private List<Stock> validStockAvailability(List<Stock> stocks, Integer quantity) {
        stocks = stocks.stream()
            .filter(stock -> stock.getCurrentQuantity() >= quantity)
            .collect(Collectors.toList());

        if (stocks.size() == 0)
            throw new NotFoundException("Non stock of product found with availability");

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
        if (existingOrderedProduct != null)
            return productsDTO.getQuantity() - existingOrderedProduct.getQuantity();
        return productsDTO.getQuantity();
    }

}
