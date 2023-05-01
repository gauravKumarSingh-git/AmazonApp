package com.amazon.paymentservice.service;

import com.amazon.paymentservice.entity.Payment;

public interface PaymentService {

    long doPayment(Payment payment);

    Payment getPaymentDetailsByOrderId(Long orderId);
    
}
