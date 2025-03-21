package com.ptho1504.microservice.order_service.order.producer;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProducerImpl implements OrderProducer {
    private final Logger logger = LoggerFactory.getLogger(OrderProducerImpl.class);
    private final KafkaTemplate<String, OrderConfirmationRequest> kafkaTemplate;

    // private final
    @Override
    public void sendOrderConfirmation(OrderConfirmationRequest orderConfirmation) {

        try {
            CompletableFuture<SendResult<String, OrderConfirmationRequest>> future = kafkaTemplate.send("order-topic",
                    orderConfirmation);
            future.whenComplete((res, ex) -> {
                if (ex == null) {
                    System.out.println(
                            "Sent message=[" + orderConfirmation.toString() + "] with offset =["
                                    + res.getRecordMetadata());
                    // logger.info(String.format("Request have been send ::%s",
                    // orderConfirmation.toString()));
                } else {
                    logger.error(ex.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("Some thing wrong");
            throw e;
        }
    }

}
