package com.amazon.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.paymentservice.entity.Payment;
import com.amazon.paymentservice.service.PaymentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    
    @Autowired
    PaymentService paymentService;

    @PostMapping(value="doPayment")
    public ResponseEntity<Long> doPayment(@RequestBody Payment payment) {
        long res = paymentService.doPayment(payment);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/getPaymentDetails/{orderId}")
    public ResponseEntity<Payment> getPaymentDetailsByOrderId (@PathVariable Long orderId){
        Payment payment = paymentService.getPaymentDetailsByOrderId(orderId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
    
    
}
