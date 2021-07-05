package com.mercadolibre.fresco.service.impl;

import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.enumeration.EProductCategory;
import com.mercadolibre.fresco.service.IProductCatalogService;
import com.mercadolibre.fresco.service.crud.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCatalogServiceImpl implements IProductCatalogService {
    private IProductService productService;
    private ModelMapper modelMapper;

    public ProductCatalogServiceImpl(IProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
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
}
