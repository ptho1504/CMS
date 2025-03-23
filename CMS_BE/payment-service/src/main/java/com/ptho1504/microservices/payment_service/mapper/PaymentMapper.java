package com.ptho1504.microservices.payment_service.mapper;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ptho1504.microservices.payment_service.dto.response.PaymentResponse;

@Component
public class PaymentMapper {
    public com.ptho1504.microservices.payment_service.payment.PaymentResponse toPaymentResponseGrpc(
            PaymentResponse paymentResponse) {
        return com.ptho1504.microservices.payment_service.payment.PaymentResponse.newBuilder()
                .setStatus(paymentResponse.getStatus())
                .setCheckoutUrl(paymentResponse.getCheckoutUrl())
                .setQrCode(paymentResponse.getQrCode())
                .build();
    }
}
