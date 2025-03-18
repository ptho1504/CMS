package com.ptho1504.microservices.order_service.order.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptho1504.microservices.payment_service.model.OrderItem;
import com.ptho1504.microservices.payment_service.model.PaymentMethod;

public class OrderConfirmationRequest {
    @JsonProperty("orderId")
    Integer orderId;

    @JsonProperty("totalPrice")
    BigDecimal totalPrice;

    @JsonProperty("customerId")
    Integer customerId;

    @JsonProperty("orderitems")
    List<OrderItem> orderitems;

    @JsonProperty("paymentMethod")
    PaymentMethod paymentMethod;
}
