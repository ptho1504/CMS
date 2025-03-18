package com.ptho1504.microservice.order_service.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CreateOrderItemRequest {
    Integer productId;
    Integer quantity;
    Double price;
}
