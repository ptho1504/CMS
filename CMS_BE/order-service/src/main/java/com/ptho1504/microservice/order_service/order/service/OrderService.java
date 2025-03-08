package com.ptho1504.microservice.order_service.order.service;

import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderRequest;

public interface OrderService {
    Object createOrder(CreateOrderRequest orderRequest);
}
