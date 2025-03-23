package com.ptho1504.microservices.payment_service.consumer;

import java.time.LocalDateTime;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.order_service.order.kafka.OrderConfirmationRequest;
import com.ptho1504.microservices.payment_service.exception.OrderExisted;
import com.ptho1504.microservices.payment_service.model.Payment;
import com.ptho1504.microservices.payment_service.model.PaymentMethod;
import com.ptho1504.microservices.payment_service.model.PaymentStatus;
import com.ptho1504.microservices.payment_service.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {
    private final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);
    private final PaymentService paymentService;

    @KafkaListener(topics = "order-topic", groupId = "order-group-1")
    public void consumePaymentSuccessNotification(OrderConfirmationRequest orderConfirmationRequest)
            throws MessagingException {
        try {
            if (orderConfirmationRequest.getPaymentMethod().equals(PaymentMethod.HOME)) {
                logger.info(String.format("----------Consuming the message from order-topic Topic-------------"));

                // Check exist of orderId
                if (this.paymentService.existsByOrderId(orderConfirmationRequest.getOrderId())) {
                    throw new OrderExisted(60002, "Order has been existed");
                }

                Payment payment = Payment.builder()
                        .createdAt(new Date())
                        .updatedAt(LocalDateTime.now())
                        .customerId(orderConfirmationRequest.getCustomerId())
                        .orderId(orderConfirmationRequest.getOrderId())
                        .totalPrice(orderConfirmationRequest.getTotalPrice())
                        .paymentMethod(PaymentMethod.HOME)
                        .status(PaymentStatus.PENDING)
                        .build();
                // Save payment
                Payment savedPayment = paymentService.savePayment(payment);

                /*
                 * Send message success to order by kafka
                 * Send message success to notify by kafka
                 */
            }
        } catch (Exception e) {
            logger.error("Some thing wrong in consumePaymentSuccessNotification", e.getMessage());
            throw e;
        }
    }
}