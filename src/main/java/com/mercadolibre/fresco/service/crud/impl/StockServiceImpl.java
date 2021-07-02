package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.repository.StockRepository;
import com.mercadolibre.fresco.service.crud.IStockService;
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
    public void delete(Long id) {

    }

    @Override
    public Stock findById(Long id) {
        return null;
    }

    @Override
    public List<Stock> findAll() {
        return null;
    }
}
