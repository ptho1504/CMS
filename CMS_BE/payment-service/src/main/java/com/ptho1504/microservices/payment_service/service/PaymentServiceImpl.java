package com.ptho1504.microservices.payment_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ptho1504.microservices.payment_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.payment_service.dto.response.PageResult;
import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.repository.PaymentRepository;
import com.ptho1504.microservices.payment_service.strategy.PaymentExecutor;

import lombok.RequiredArgsConstructor;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentExecutor paymentExecutor;
    private final PayOS payOS;

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
            return data;
        } catch (Exception e) {
            logger.error("Some thing wrong in handleWebHook");
            throw new RuntimeException("handleWebHook failed", e);
        }
    }

    @Override
    public Object handlePayment(Payment payment) {
        try {

            return this.paymentExecutor.processPayment(payment.getPaymentMethod(), payment);
        } catch (Exception e) {
            logger.error("Some thing wrong in handleWebHook");
            throw new RuntimeException("handlePayment failed", e);
        }
    }

}
