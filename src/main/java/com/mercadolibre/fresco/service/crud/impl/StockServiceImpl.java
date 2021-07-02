package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.ApiException;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.repository.StockRepository;
import com.mercadolibre.fresco.service.crud.IStockService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void delete(Long id) {}

    @Override
    public Stock findById(Long id) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if(stock != null){
            return stock;
        }else{
            throw new ApiException("404", "Stock not found", 404);
        }
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> stocks = stockRepository.findAll();

        if(stocks.size() == 0){
            throw new ApiException("404", "Stock not found", 404);
        }else{
            return stocks;
        }
    }

    @Override
    public Stock updateCurrentQuantityById(Long id, Integer cur_quantity) {
        this.findById(id);
        if(cur_quantity < 0){
            throw new ApiException("400", "Current Quantity cannot be less than 0.", 400);
        }
        return stockRepository.updateCurrentQuantityById(id, cur_quantity);
    }

    @Override
    public List<Stock> checkStockWithProductAvailability(String productCode, int quantity) {
        List<Stock> stocks = stockRepository.findByProductCodeWithCurrentQuantity(productCode, quantity);
        if(stocks.size() > 0){
            return stocks;
        }else{
            throw new ApiException("400", "Not enough Product " + productCode + " available units for this purchase.", 400);
        }
    }
}
