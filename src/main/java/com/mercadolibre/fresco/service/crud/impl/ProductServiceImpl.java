package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
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
        if (this.productRepository.findByProductCode(product.getProductCode()) == null){
            throw new BadRequestException("Product " + product.getProductCode() + " already exists!");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        if (this.productRepository.findByProductCode(product.getProductCode()) == null){
            throw new NotFoundException("Product " + product.getProductCode() + " not found!");
        }

        return this.productRepository.save(product);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public Product findByProductCode(String productCode) {
        productCode = productCode.toUpperCase();
        return this.productRepository.findByProductCode(productCode);
    }

    @Override
    public List<Product> findAll() {
        List<Product> products =  this.productRepository.findAll();
        if (products.isEmpty()){
            throw new NotFoundException("Products not exists!");
        }
        return products;
    }

    @Override
    public List<Product> findProductsByCategoryCode(String categoryCode) {
        List<Product> products = this.productRepository.findByProductCategory(categoryCode);
        if (products.isEmpty()){
            throw new NotFoundException("Products not exists!");
        }
        return products;
    }
}
