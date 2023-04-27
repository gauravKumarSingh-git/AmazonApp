package com.amazon.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
