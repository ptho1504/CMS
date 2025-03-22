package com.ptho1504.microservices.payment_service.strategy;

import com.ptho1504.microservices.payment_service.model.Payment;

public interface PaymentStrategy {
    void register();

    Object handlePayment(Payment payment);
}
