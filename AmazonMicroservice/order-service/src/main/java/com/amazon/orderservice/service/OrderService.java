package com.amazon.orderservice.service;

import com.amazon.orderservice.entity.Orders;
import com.amazon.orderservice.exception.OrderException;

public interface OrderService {

    Long placeOrder(Orders order) throws OrderException;
    
}
