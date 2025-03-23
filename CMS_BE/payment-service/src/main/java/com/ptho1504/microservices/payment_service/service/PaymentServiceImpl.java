package com.ptho1504.microservices.payment_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ptho1504.microservices.notify_service.notify.kafka.PaymentEvent;
import com.ptho1504.microservices.payment_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.payment_service.dto.response.PageResult;
import com.ptho1504.microservices.payment_service.dto.response.PaymentResponse;
import com.ptho1504.microservices.payment_service.exception.PaymentNotFound;
import com.ptho1504.microservices.payment_service.exception.PaymentPermission;
import com.ptho1504.microservices.payment_service.mapper.PaymentMapper;
import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.model.PaymentMethod;
import com.ptho1504.microservices.payment_service.model.PaymentStatus;
import com.ptho1504.microservices.payment_service.model.PaymentTransaction;
import com.ptho1504.microservices.payment_service.payment.PaymentSerivceGrpc.PaymentSerivceImplBase;
import com.ptho1504.microservices.payment_service.producer.PaymentProducer;
import com.ptho1504.microservices.payment_service.repository.PaymentRepository;
import com.ptho1504.microservices.payment_service.strategy.PaymentExecutor;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@Service
@GrpcService
@RequiredArgsConstructor
public class PaymentServiceImpl extends PaymentSerivceImplBase implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentExecutor paymentExecutor;
    private final PaymentProducer paymentProducer;
    private final PayOS payOS;
    private final PaymentMapper mapper;

    @Override
    public PageResult<Object> findAll(PaginationRequest requestFindAll) {
        try {

            return null;
        } catch (Exception e) {
            logger.error("Some thing wrong in find All");
            throw e;
        }
    }

    @Override
    public boolean existsByOrderId(Integer orderId) {
        try {
            return this.paymentRepository.existsByOrderId(orderId);
        } catch (Exception e) {
            logger.error("Some thing wrong in existsByOrderId");
            throw e;
        }
    }

    @Override
    public Payment savePayment(Payment payment) {
        try {
            return this.paymentRepository.save(payment);
        } catch (Exception e) {
            logger.error("Some thing wrong in savePayment");
            throw e;
        }
    }

    @Override
    public Object handlePayOsWebHook(ObjectNode body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode response = objectMapper.createObjectNode();
            Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);
            WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);

            System.out.println("data: " + data);
            // Handle logic
            if (!data.getDesc().equals("success")) {
                throw new RuntimeException("Webhook not success");
            }

            Integer orderId = data.getOrderCode().intValue();

            // Check existence of Order in payments
            Optional<Payment> paymentOptional = this.paymentRepository.findByOrderId(orderId);

            if (paymentOptional.isEmpty()) {
                throw new PaymentNotFound(60003, "Not found payment");
            }

            Payment payment = paymentOptional.get();

            // Check existence of payment
            if (!payment.getStatus().equals(PaymentStatus.PENDING)) {
                throw new PaymentPermission(60003, "Payment not change");
            }

            // Save transaction Payment
            // Change status payment
            payment.setStatus(PaymentStatus.COMPLETED);
            PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                    .payment(payment)
                    .amount(payment.getTotalPrice())
                    .currency(data.getCurrency())
                    .accountNumber(data.getAccountNumber())
                    .accountName(data.getCounterAccountName())
                    .description(data.getDescription())
                    .status(PaymentStatus.COMPLETED)
                    .createdAt(new Date())
                    .updatedAt(LocalDateTime.now())
                    .paymentLinkId(data.getPaymentLinkId())
                    .build();
            payment.setPaymentTransaction(paymentTransaction);

            // Save new payment
            this.savePayment(payment);

            // Send successful to Kafka
            PaymentEvent paymentEvent = PaymentEvent.builder()
                    .paymentId(payment.getId())
                    .orderId(payment.getOrderId())
                    .customerId(payment.getCustomerId())
                    .totalPrice(payment.getTotalPrice())
                    .status(payment.getStatus())
                    .build();
            paymentProducer.sendOrderConfirmation(paymentEvent);
            return data;
        } catch (Exception e) {
            logger.error("Some thing wrong in handleWebHook ", e.getMessage());
            throw new RuntimeException("handleWebHook failed", e);
        }
    }

    // Grpc - start

    @Override
    public void handlePayment(com.ptho1504.microservices.payment_service.payment.Payment request,
            StreamObserver<com.ptho1504.microservices.payment_service.payment.PaymentResponse> responseObserver) {
        logger.info("Received gRPC request for handlePayment with Order Id  " + request.getOrderId());

        BigDecimal totalAmount = new BigDecimal(request.getTotalPrice());
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod().name());
        // PaymentStatus paymentStatus =
        // PaymentStatus.valueOf(request.getStatus().name());
        // PaymentStatus paymentStatus =
        // PaymentStatus.valueOf(request.getStatus().name());

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .totalPrice(totalAmount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .createdAt(new Date())
                .updatedAt(LocalDateTime.now())
                .build();

        // save payment
        Payment savedPayment = this.savePayment(payment);

        PaymentResponse response = this.paymentExecutor.processPayment(savedPayment.getPaymentMethod(), savedPayment);
        com.ptho1504.microservices.payment_service.payment.PaymentResponse res = this.mapper
                .toPaymentResponseGrpc(response);
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public Object test(PaymentEvent request) {
        paymentProducer.sendOrderConfirmation(request);
        return null;
    }

    // Grpc - end

}
