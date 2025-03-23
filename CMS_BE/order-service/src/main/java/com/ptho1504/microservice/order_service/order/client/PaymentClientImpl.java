package com.ptho1504.microservice.order_service.order.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order.dto.response.PaymentResponse;
import com.ptho1504.microservices.payment_service.payment.Payment;
import com.ptho1504.microservices.payment_service.payment.PaymentSerivceGrpc;
import com.ptho1504.microservices.payment_service.payment.PaymentSerivceGrpc.PaymentSerivceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Service
public class PaymentClientImpl implements PaymentClient {
    Logger logger = LoggerFactory.getLogger(PaymentClientImpl.class);

    private PaymentSerivceBlockingStub paymentSerivceBlockingStub;

    public PaymentClientImpl(@Value("${grpc-server.payment.service.address}") String paymentServiceAddress) {
        System.out.println("Initializing PaymentClientImpl with gRPC address: " +
                paymentServiceAddress);
        ManagedChannel channel = ManagedChannelBuilder.forTarget(paymentServiceAddress)
                .usePlaintext() // In production, you'd use SSL/TLS
                .build();
        System.out.println("Initializing PaymentClientImpl with gRPC address: " +
                channel);
        this.paymentSerivceBlockingStub = PaymentSerivceGrpc.newBlockingStub(channel);

    }

    @Override
    public PaymentResponse handlePayment(Payment reqPayment) {
        try {
            com.ptho1504.microservices.payment_service.payment.PaymentResponse response = this.paymentSerivceBlockingStub
                    .handlePayment(reqPayment);
            PaymentResponse paymentResponse = PaymentResponse.builder()
                    .checkoutUrl(response.getCheckoutUrl())
                    .qrCode(response.getQrCode())
                    .status(response.getStatus())
                    .build();
            return paymentResponse;
        } catch (Exception e) {
            logger.error("An error occurred while CustomerClientImpl", e.getMessage());
            throw e;
        }
    }

}
