package com.amazon.orderservice.service;


import com.amazon.orderservice.entity.Orders;
import com.amazon.orderservice.exception.OrderException;
import com.amazon.orderservice.model.OrderResponse;

public interface OrderService {

    Long placeOrder(Orders order) throws OrderException;

    OrderResponse getOrderDetails(Long orderId) throws OrderException;
    
}
