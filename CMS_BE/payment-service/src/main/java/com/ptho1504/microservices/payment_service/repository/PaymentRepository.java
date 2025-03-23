package com.ptho1504.microservices.payment_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptho1504.microservices.payment_service.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    public boolean existsByOrderId(Integer orderId);

    public Optional<Payment> findByOrderId(Integer orderId);
}
