package com.mercadolibre.fresco.service.crud.impl;

import com.mercadolibre.fresco.exceptions.BadRequestException;
import com.mercadolibre.fresco.exceptions.NotFoundException;
import com.mercadolibre.fresco.model.Product;
import com.mercadolibre.fresco.repository.ProductRepository;
import com.mercadolibre.fresco.service.crud.IProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product create(Product product) {
        if (productRepository.findById(product.getProductId()) != null){
            throw new BadRequestException("Product already exists");
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product update(Product product) {
        if (productRepository.findById(product.getProductId()) == null){
            throw new NotFoundException("Product not found!");
        }
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!productRepository.findById(id).isPresent()){
            throw new NotFoundException("Product not found!");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(Long id) {
        Product product = productRepository.findById(id).get();
        if (product == null) {
            throw new NotFoundException("Product not found!");
        }
        return product;
    }

    @Override
    @Transactional
    public Product findById(String id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new NotFoundException("Product not found!");
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()){
            throw new NotFoundException("Product not found!");
        }
        return products;
    }
}
