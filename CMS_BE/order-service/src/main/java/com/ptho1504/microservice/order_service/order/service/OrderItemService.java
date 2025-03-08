package com.ptho1504.microservice.order_service.order.service;

import java.util.Optional;

import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderItemRequest;
import com.ptho1504.microservice.order_service.order.dto.request.UpdateOrderItemRequest;
import com.ptho1504.microservice.order_service.order.model.Order;
import com.ptho1504.microservice.order_service.order.model.OrderItem;

public interface OrderItemService {
    public Optional<OrderItem> findById(Integer orderId);

    public OrderItem createOrderItem(CreateOrderItemRequest request);

    public OrderItem saveOrderItem(OrderItem orderItem);

    public void deleteById(Integer orderId);

    public OrderItem updateOrderItem(Integer orderId, UpdateOrderItemRequest request);
}
