package com.ptho1504.microservices.payment_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PaymentResponse {
    String status;
    String checkoutUrl;
    String qrCode;
}
