package com.amazon.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.orderservice.entity.Orders;
import com.amazon.orderservice.exception.OrderException;
import com.amazon.orderservice.model.OrderResponse;
import com.amazon.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody Orders order) throws OrderException{
        Long orderId = orderService.placeOrder(order);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/getOrderDetails/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable Long orderId) throws OrderException{
        return new ResponseEntity<>(orderService.getOrderDetails(orderId), HttpStatus.OK);
    }
}
