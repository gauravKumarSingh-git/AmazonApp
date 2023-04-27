package com.amazon.productservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.productservice.entity.Product;
import com.amazon.productservice.exception.ProductException;
import com.amazon.productservice.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public long addProduct(Product product) {
        log.info("Adding Product...");
        productRepository.save(product);
        log.info("Product created");
        return product.getProductId();
    }

    @Override
    public Product getProductById(long productId) throws ProductException{
        log.info("Get product for product Id " + productId);

        Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new ProductException("Product for given Product Id not present"));
        
        return product;
    }

    @Override
    public void reduceQuantity(long id, long quantity) throws ProductException {
        Product product = productRepository.findById(id)
                            .orElseThrow(() -> new ProductException("Product with give Id not found"));
        if(product.getQuantity() < quantity){
            throw new ProductException("Product does not have sufficient quantity");
        }

        product.setQuantity(product.getQuantity() - quantity);
        log.info("product quantity successfully updated");
    }
    
}
