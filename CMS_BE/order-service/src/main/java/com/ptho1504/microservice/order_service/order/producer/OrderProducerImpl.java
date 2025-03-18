package com.ptho1504.microservice.order_service.order.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest;

// import com.ptho1504.microservice.order_service.order.dto.request.OrderConfirmationRequest;
import org.springframework.messaging.Message;
import lombok.RequiredArgsConstructor;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OrderProducerImpl implements OrderProducer {
    private final Logger logger = LoggerFactory.getLogger(OrderProducerImpl.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // private final
    @Override
    public void sendOrderConfirmation(OrderConfirmationRequest orderConfirmation) {

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("order-topic", orderConfirmation);
            future.whenComplete((res, ex) -> {
                if (ex == null) {
                    System.out.println(
                            "Sent message=[" + orderConfirmation.toString() + "] with offset =["
                                    + res.getRecordMetadata());
                    // logger.info(String.format("Request have been send ::%s", orderConfirmation.toString()));
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
