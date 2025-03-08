package com.ptho1504.microservice.order_service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeductStockMessage {
    String message;
    Boolean success;
}
