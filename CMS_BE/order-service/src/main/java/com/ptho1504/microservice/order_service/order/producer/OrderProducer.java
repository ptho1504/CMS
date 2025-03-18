package com.ptho1504.microservice.order_service.order.producer;

import com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest;

public interface OrderProducer {
    public void sendOrderConfirmation(OrderConfirmationRequest orderConfirmation);

}
