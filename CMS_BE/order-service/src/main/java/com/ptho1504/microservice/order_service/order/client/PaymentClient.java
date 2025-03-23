package com.ptho1504.microservice.order_service.order.client;

import com.ptho1504.microservice.order_service.order.dto.response.PaymentResponse;
import com.ptho1504.microservices.payment_service.payment.Payment;

public interface PaymentClient {
    PaymentResponse handlePayment(Payment reqPayment);
}