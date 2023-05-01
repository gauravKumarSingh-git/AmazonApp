package com.amazon.orderservice.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazon.orderservice.entity.Orders;
import com.amazon.orderservice.exception.OrderException;
import com.amazon.orderservice.external.client.PaymentService;
import com.amazon.orderservice.external.client.ProductException;
import com.amazon.orderservice.external.client.ProductService;
import com.amazon.orderservice.external.request.Payment;
import com.amazon.orderservice.model.OrderResponse;
import com.amazon.orderservice.model.OrderResponse.ProductResponse;
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

    @Autowired
    RestTemplate restTemplate;

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

    @Override
    public OrderResponse getOrderDetails(Long orderId) throws OrderException {
        log.info("get order details for order id " + orderId.toString());
        Orders order = 
            orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderException("Order not found for ID " + orderId));

        log.info("invoking product service to get product for order ID " + orderId);
        ProductResponse product = restTemplate.getForObject("http://PRODUCT-SERVICE/product/getProduct/" + order.getProductId(), ProductResponse.class);
        
        OrderResponse.ProductResponse productDetails = 
            OrderResponse.ProductResponse
            .builder()
            .productName(product.getProductName())
            .productId(product.getProductId())
            .build();   

        log.info("getting payment information from order id " + order.getId());

        Payment payment = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/getPaymentDetails/" + order.getId(), Payment.class );

        OrderResponse.PaymentResponse paymentResponse = 
            OrderResponse.PaymentResponse
            .builder()
            .id(payment.getId())
            .paymentMode(payment.getPaymentMode())
            .referenceNumber(payment.getReferenceNumber())
            .paymentDate(payment.getPaymentDate())
            .paymentStatus(payment.getPaymentStatus())
            .amount(payment.getAmount())
            .build();

        OrderResponse orderResponse = 
            OrderResponse
            .builder()
            .id(order.getId())
            .orderStatus(order.getOrderStatus())
            .amount(order.getAmount())
            .orderDate(order.getOrderDate())
            .productResponse(productDetails)
            .paymentResponse(paymentResponse)
            .build();
        
        return orderResponse;
    }
    
}
