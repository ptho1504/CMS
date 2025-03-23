package com.ptho1504.microservices.payment_service.strategy;

import com.ptho1504.microservices.payment_service.dto.response.PaymentResponse;
import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.model.PaymentMethod;

public interface PaymentExecutor {
    PaymentResponse processPayment(PaymentMethod paymentMethod, Payment payment);
}
