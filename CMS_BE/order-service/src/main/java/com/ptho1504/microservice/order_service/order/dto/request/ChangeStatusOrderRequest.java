package com.ptho1504.microservice.order_service.order.dto.request;

import com.ptho1504.microservice.order_service.order._enum.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusOrderRequest {
    private Integer userId;
    private Integer orderId;
    private OrderStatus status;
}
