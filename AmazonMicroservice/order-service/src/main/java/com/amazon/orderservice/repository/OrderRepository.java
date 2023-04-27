package com.amazon.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.orderservice.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    
}
