package com.ptho1504.microservices.notify_service.notify.kafka;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ptho1504.microservices.payment_service.model.PaymentStatus;

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
public class PaymentEvent {
    @JsonProperty("paymentId")
    private Integer paymentId;

    @JsonProperty("orderId")
    private Integer orderId;

    @JsonProperty("customerId")
    private Integer customerId;

    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    @JsonProperty("status")
    private PaymentStatus status;
}
