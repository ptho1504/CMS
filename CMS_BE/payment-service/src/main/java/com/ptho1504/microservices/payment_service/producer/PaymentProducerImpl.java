package com.ptho1504.microservices.payment_service.producer;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.notify_service.notify.kafka.PaymentEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentProducerImpl implements PaymentProducer {
    private final Logger logger = LoggerFactory.getLogger(PaymentProducerImpl.class);
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Override
    public void sendOrderConfirmation(PaymentEvent paymentEvent) {
        try {
            CompletableFuture<SendResult<String, PaymentEvent>> future = kafkaTemplate.send("payment-topic",
                    paymentEvent);
            future.whenComplete((res, ex) -> {
                if (ex == null) {
                    System.out.println(
                            "Sent message=[" + paymentEvent.toString() + "] with offset =["
                                    + res.getRecordMetadata());
                    // logger.info(String.format("Request have been send ::%s",
                    // orderConfirmation.toString()));
                } else {
                    logger.error(ex.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("Some thing wrong", e.getMessage());
            throw e;
        }

    }

}
