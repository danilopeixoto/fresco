package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.dtos.response.ProductResponseDTO;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.repository.ProductRepository;
import com.mercadolibre.fresco.service.crud.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductResponseDTO create(Product product) {
        return this.modelMapper.map(
            productRepository.save(product),
            ProductResponseDTO.class);
    }

    public ProductResponseDTO update(Product product) {
        return null;
    }

    @Override
    public void delete(Long id) {}

    @Override
    public ProductResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public ProductResponseDTO findByProductCode(String productCode) {
        productCode = productCode.toUpperCase();

        return this.modelMapper.map(
            this.productRepository.findByProductCode(productCode),
            ProductResponseDTO.class);
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(product -> this.modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findProductsByCategoryCode(String categoryCode) {
        return this.productRepository
                .findByProductCategory(categoryCode)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }
}
