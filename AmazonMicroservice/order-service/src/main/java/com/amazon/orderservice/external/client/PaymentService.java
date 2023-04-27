package com.amazon.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amazon.orderservice.external.request.Payment;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {


    @PostMapping(value="doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody Payment payment);
}
