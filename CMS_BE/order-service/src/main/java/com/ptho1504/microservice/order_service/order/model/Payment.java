package com.ptho1504.microservice.order_service.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Payment {
    String status;
    String checkoutUrl;
    String qrCode;
    PaymentMethod payMethod;
}
