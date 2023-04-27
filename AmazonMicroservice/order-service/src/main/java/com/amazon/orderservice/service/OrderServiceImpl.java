package com.amazon.orderservice.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.orderservice.entity.Orders;
import com.amazon.orderservice.exception.OrderException;
import com.amazon.orderservice.external.client.PaymentService;
import com.amazon.orderservice.external.client.ProductException;
import com.amazon.orderservice.external.client.ProductService;
import com.amazon.orderservice.external.request.Payment;
import com.amazon.orderservice.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentService paymentService;

    @Override
    public Long placeOrder(Orders order) throws OrderException{

        try {
            productService.reduceQuantity(order.getProductId(), order.getQuantity());
        } catch (ProductException e) {
            throw new OrderException(e.getMessage());
        }
        order.setOrderDate(LocalDate.now());
        order.setPaymentDate(LocalDate.now());
        order.setOrderStatus("CREATED");
        orderRepository.save(order);

        log.info("Calling payment service to complete the payment");
        Payment payment = new Payment();
        payment.setPaymentMode(order.getPaymentMode());
        payment.setOrderId(order.getId());
        payment.setAmount(order.getAmount());
        
        String orderStatus = null;
        try{
            paymentService.doPayment(payment);
            log.info("Payment done successfully changing order status to placed");
            orderStatus = "PLACED";
        }
        catch(Exception e){
            log.info("Payment failed changing order status to PAYMENT_FAILED");
            log.info(e.getMessage());
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return order.getId();
    }
    
}
