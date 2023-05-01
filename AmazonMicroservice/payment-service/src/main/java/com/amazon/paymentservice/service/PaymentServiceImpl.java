package com.amazon.paymentservice.service;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.paymentservice.entity.Payment;
import com.amazon.paymentservice.repository.PaymentRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository; 

    @Override
    public long doPayment(Payment payment) {
        log.info("Saving payment " + payment.getId());
        payment.setPaymentDate(Instant.now());
        payment.setPaymentStatus("SUCCESS");
        paymentRepository.save(payment);
        log.info("Payment details saved successsfully with id " + payment.getId());
        return payment.getId();
    }

    @Override
    public Payment getPaymentDetailsByOrderId(Long orderId) {
        log.info("getting payment details for order id " + orderId);
        Payment payment = paymentRepository.findByOrderId(orderId);
        return payment;
    }
    
}
