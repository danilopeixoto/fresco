package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.InfoStockDTO;
import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.dtos.response.ProductStockResponseDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Stock;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.service.IProductCatalogService;
import com.mercadolibre.fresco.service.crud.IProductService;
import com.mercadolibre.fresco.service.crud.IStockService;
import com.mercadolibre.fresco.service.crud.IWarehouseSection;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public List<ProductStockResponseDTO> findStocksByProductCode(String productCode) {
        List<InfoStockDTO> stocks = stockService.findWithSectionAndWarehouseByProductCode(productCode);

        return null;
    }
}
