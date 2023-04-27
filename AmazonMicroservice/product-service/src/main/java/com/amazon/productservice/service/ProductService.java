package com.amazon.productservice.service;

import com.amazon.productservice.entity.Product;
import com.amazon.productservice.exception.ProductException;

public interface ProductService {

    long addProduct(Product product);

    Product getProductById(long productId) throws ProductException;

    void reduceQuantity(long id, long quantity) throws ProductException;
    
}
