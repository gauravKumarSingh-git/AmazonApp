package com.amazon.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amazon.orderservice.external.request.Payment;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name= "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {


    @PostMapping(value="doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody Payment payment);

    default void fallback(Exception e){
        throw new RuntimeException("Payment Service is not available ");
    }
}
