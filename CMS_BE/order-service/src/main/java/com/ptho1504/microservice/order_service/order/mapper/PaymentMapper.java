package com.ptho1504.microservice.order_service.order.mapper;

import org.springframework.stereotype.Component;

import com.ptho1504.microservice.order_service.order.dto.response.PaymentResponse;
import com.ptho1504.microservice.order_service.order.model.Payment;

@Component
public class PaymentMapper {
    public Payment toPayment(PaymentResponse response) {
        return Payment.builder()
                .checkoutUrl(response.getCheckoutUrl())
                .status(response.getStatus())
                .qrCode(response.getQrCode())
                .build();
    }
}
