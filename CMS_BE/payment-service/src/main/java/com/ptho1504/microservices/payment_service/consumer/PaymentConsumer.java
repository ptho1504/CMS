package com.ptho1504.microservices.payment_service.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {
    private final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);

    @KafkaListener(topics = "order-topic", groupId = "order-group-1")
    public void consumePaymentSuccessNotification(Object orderConfirmationRequest)
            throws MessagingException {
        try {
            logger.info(String.format("----------Consuming the message from order-topic Topic-=------------"));
            System.out.println(orderConfirmationRequest.toString());
            // logger.info(orderConfirmationRequest.toString());

        } catch (Exception e) {
            logger.error("Some thing wrong");
            throw e;
        }

    }

}