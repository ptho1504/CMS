package com.ptho1504.microservice.order_service.order.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptho1504.microservice.order_service.order.model.OrderItem;
import com.ptho1504.microservice.order_service.order.model.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "OrderConfirmationRequest{" +
                "orderId=" + orderId +
                ", totalPrice=" + totalPrice +
                ", customerId=" + customerId +
                ", orderitems=" + orderitems +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
