package com.ptho1504.microservice.order_service.order.consumer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order._enum.OrderStatus;
import com.ptho1504.microservice.order_service.order.exception.OrderNotFound;
import com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest;
import com.ptho1504.microservice.order_service.order.kafka.PaymentEvent;
import com.ptho1504.microservice.order_service.order.model.Order;
import com.ptho1504.microservice.order_service.order.service.OrderService;
import com.ptho1504.microservices.payment_service.payment.PaymentStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderConsumer {
    private final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    private final OrderService orderService;

    @KafkaListener(topics = "payment-topic", groupId = "payment-group-1")
    public void consumePaymentEvent(PaymentEvent paymentEvent)
            throws MessagingException {
        try {
            logger.info(String.format("----------Consuming the message from payment-topic Topic-------------"));
            System.out.println("Payment Event" + paymentEvent);

            Integer orderId = paymentEvent.getOrderId();
            Integer cusId = paymentEvent.getCustomerId();

            // Check Order and CustomerId
            Optional<Order> orderOptional = this.orderService.findByOrderIdAndCustomerId(orderId, cusId);

            if (orderOptional.isEmpty()) {
                throw new OrderNotFound(50001, "Not found Order");
            }

            Order order = orderOptional.get();

            if (!order.getStatus().equals(OrderStatus.PENDING)) {
                return;
            }
            if (!paymentEvent.getStatus().equals(PaymentStatus.COMPLETED)) {
                return;
            }
            order.setStatus(OrderStatus.PROCESSING);
            this.orderService.saveOrder(order);
        } catch (Exception e) {
            logger.error("Some thing wrong in consumePaymentSuccessNotification", e.getMessage());
            throw e;
        }
    }
}
