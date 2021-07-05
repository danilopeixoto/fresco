package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.repository.StockRepository;
import com.mercadolibre.fresco.service.crud.IStockService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements IStockService {

    private StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
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
    public List<Stock> findByProductCode(String productCode) {
        List<Stock> stocks = this.stockRepository.findByProductCode(productCode);
        if (stocks.isEmpty()) {
            throw new NotFoundException("Products not found");
        }

        LocalDate futureTime = LocalDate.now().plusWeeks(3);

        List<Stock> validStock = stocks.stream()
                .filter(validDate -> validDate.getProduct().getDueDate().isAfter(futureTime))
                .collect(Collectors.toList());

        return validStock;
    }


    @Override
    public Stock updateCurrentQuantityById(Long id, Integer cur_quantity) {
        this.findById(id);
        if (cur_quantity < 0) {
            throw new ApiException("400", "Current Quantity cannot be less than 0.", 400);
        }
        return stockRepository.updateCurrentQuantityById(id, cur_quantity);
    }

    @Override
    public List<Stock> checkStockWithProductAvailability(String productCode, int quantity) {
        List<Stock> stocks = stockRepository.findByProductCodeWithCurrentQuantity(productCode, quantity);
        if (stocks.isEmpty()) {
            throw new ApiException("400", "Not enough Product " + productCode + " available units for this purchase.", 400);
        }
        return stocks;
    }

    @Override
    public void deleteByBatchNumber(Integer batchNumber) {
        this.stockRepository.deleteByBatchNumber(batchNumber);
    }
}
