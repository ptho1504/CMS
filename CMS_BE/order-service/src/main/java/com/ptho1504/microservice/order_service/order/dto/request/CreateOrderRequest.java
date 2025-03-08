package com.ptho1504.microservice.order_service.order.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CreateOrderRequest {
    private Integer userId;
    @NotNull(message = "List Order Item is required")
    @Size(min = 1, message = "Username must be at least 3 characters long")
    List<CreateOrderItemRequest> orderItems;

}
