package com.mercadolibre.fresco.util.impl;

import com.mercadolibre.fresco.dtos.SectionDTO;
import com.mercadolibre.fresco.dtos.StockDTO;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IWarehouseSection;
import com.mercadolibre.fresco.service.crud.IWarehouseService;
import com.mercadolibre.fresco.service.crud.impl.ProductServiceImpl;
import com.mercadolibre.fresco.util.StockUtils;

public class StockUtilsImpl implements StockUtils {

    private IProductService productService;
    private IWarehouseSection warehouseSection;

    public StockUtilsImpl(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public Stock convertDtoToEntity(StockDTO stockDTO, SectionDTO sectionDTO) {
        Stock stock = new Stock();
        stock.setCurrentQuantity(stockDTO.getCurrentQuantity());
        stock.setCurrentTemperature(stockDTO.getCurrentTemperature());
        stock.setInitialQuantity(stockDTO.getInitialQuantity());
        stock.setProduct(productService.findById(stockDTO.getProductId()));
       // stock.setWarehouseSection(warehouseSection.findByWarehouseCodeAndSectionCode(sectionDTO.getWarehouseCode(), sectionDTO.getSectionCode()));
        return stock;
    }
}
