package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductStockResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IBatchStockDueDateResponseDTO;
import com.mercadolibre.fresco.dtos.response.aggregation.IInfoStockDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.enumeration.BatchStockOrder;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.model.enumeration.EResultOrder;
import com.mercadolibre.fresco.service.IProductCatalogService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IStockService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCatalogServiceImpl implements IProductCatalogService {
    private IProductService productService;
    private ModelMapper modelMapper;
    private IStockService stockService;

    public ProductCatalogServiceImpl(IProductService productService, ModelMapper modelMapper, IStockService stockService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.stockService = stockService;
    }

    @Override
    public List<ProductResponseDTO> findAll() throws NotFoundException {
        List<ProductResponseDTO> products = this.productService
            .findAll()
            .stream()
            .map(product -> this.modelMapper.map(product, ProductResponseDTO.class))
            .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new NotFoundException("Product list not found.");
        }

        return products;
    }

    @Override
    public List<ProductResponseDTO> findProductsByCategoryCode(EProductCategory category) throws NotFoundException {
        List<ProductResponseDTO> products = this.productService
            .findProductsByCategoryCode(category.getCategory())
            .stream()
            .map(product -> this.modelMapper.map(product, ProductResponseDTO.class))
            .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new NotFoundException("Product list not found.");
        }

        return products;
    }

    @Override
    public ProductStockResponseDTO findStocksByProductCode(String username, String productCode, BatchStockOrder order) {
        List<IInfoStockDTO> stocks = stockService.findWithSectionAndWarehouseByProductCode(username, productCode);

        if (order.getOrder().equals("c"))
            stocks.sort(Comparator.comparing(IInfoStockDTO::getCurrentQuantity));
        else
            stocks.sort(Comparator.comparing(IInfoStockDTO::getDueDate));

        ProductStockResponseDTO productStockResponseDTO = ProductStockResponseDTO.builder()
            .productId(productCode)
            .batchStock(stocks)
            .build();

        return productStockResponseDTO;
    }

    @Override
    public List<IBatchStockDueDateResponseDTO> findStocksByDueDate(Integer dayQuantity) {
        List<IBatchStockDueDateResponseDTO> stocks = stockService.findStockWithProductDueDateUntilFutureDate(dayQuantity);
        return stocks;
    }

    @Override
    public List<IBatchStockDueDateResponseDTO> findStocksByDueDateAndProductCategory(Integer dayQuantity, EProductCategory productCategory, EResultOrder order) {
        List<IBatchStockDueDateResponseDTO> stocks = stockService.findStockWithProductDueDateUntilFutureByProductCategory(dayQuantity, productCategory.getCategory());
        if(order.getOrder().equals("asc"))
            stocks.sort(Comparator.comparing(IBatchStockDueDateResponseDTO::getDueDate));
        else
            stocks.sort((a,b) -> b.getDueDate().compareTo(a.getDueDate()));

        return stocks;
    }


}
