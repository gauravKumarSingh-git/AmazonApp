package com.amazon.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    Payment findByOrderId(Long orderId);
}
