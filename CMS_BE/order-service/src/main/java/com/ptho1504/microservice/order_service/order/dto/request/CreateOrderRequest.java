package com.ptho1504.microservice.order_service.order.dto.request;

import java.util.List;

import com.ptho1504.microservice.order_service.order.model.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CreateOrderRequest {
    // @NotNull(message = "User Id is required")
    private Integer userId;
    @NotNull(message = "List Order Item is required")
    @Size(min = 1, message = "Username must be at least 3 characters long")
    List<CreateOrderItemRequest> orderItems;
    @NotNull(message = "Payment Method Item is required")
    PaymentMethod paymentMethod;
}
