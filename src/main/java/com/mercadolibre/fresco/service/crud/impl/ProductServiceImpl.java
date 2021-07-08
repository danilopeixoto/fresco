package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.ApiException;
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
        if (this.productRepository.findByProductCode(product.getProductCode()) == null) {
            throw new ApiException("409", "Product " + product.getProductCode() + " already exists!", 409);
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        if (this.productRepository.findByProductCode(product.getProductCode()) == null) {
            throw new ApiException("404","Product " + product.getProductCode() + " not found!", 404);
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
        List<Product> products = this.productRepository.findAll();
        if (products.isEmpty()) {
            throw new ApiException("404", "Products not exists!", 404);
        }
        return products;
    }

    @Override
    public List<Product> findProductsByCategoryCode(String categoryCode) {
        List<Product> products = this.productRepository.findByProductCategory(categoryCode);

        if (products.isEmpty()) {
            throw new ApiException("404", "Products in category "+categoryCode+" not exists!", 404);
        }

        return products;
    }
}
