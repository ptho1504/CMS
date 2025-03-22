package com.ptho1504.microservices.payment_service.strategy;

import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.model.PaymentMethod;

public interface PaymentExecutor {
    Object processPayment(PaymentMethod paymentMethod, Payment payment);
}
