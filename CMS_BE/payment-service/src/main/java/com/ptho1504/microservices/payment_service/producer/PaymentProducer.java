package com.ptho1504.microservices.payment_service.producer;

import com.ptho1504.microservices.notify_service.notify.kafka.PaymentEvent;

public interface PaymentProducer {
    public void sendOrderConfirmation(PaymentEvent paymentEvent);

}
