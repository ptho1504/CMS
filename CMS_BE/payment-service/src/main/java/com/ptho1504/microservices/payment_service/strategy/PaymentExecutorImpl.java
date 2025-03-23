package com.ptho1504.microservices.payment_service.strategy;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.ptho1504.microservices.payment_service.dto.response.PaymentResponse;
import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.model.PaymentMethod;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentExecutorImpl implements PaymentExecutor {
    private static HashMap<PaymentMethod, PaymentStrategy> paymentStrategyMap = new HashMap<>();

    public static void addPaymentStrategy(PaymentMethod paymentMethod, PaymentStrategy paymentStrategy) {
        paymentStrategyMap.put(paymentMethod, paymentStrategy);
    }

    @Override
    public PaymentResponse processPayment(PaymentMethod paymentMethod, Payment payment) {
        return paymentStrategyMap.get(paymentMethod)
                .handlePayment(payment);
    }
}
