package com.ptho1504.microservice.order_service.order.dto.response;

import com.ptho1504.microservice.order_service.order.model.Order;
import com.ptho1504.microservice.order_service.order.model.Payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
    CustomerRespone customer;
    Order order;
    Payment payment;
}
