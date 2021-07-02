package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.repository.ProductRepository;
import com.mercadolibre.fresco.service.crud.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public Product findById(String id) {
        return this.productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return null;
    }
}
