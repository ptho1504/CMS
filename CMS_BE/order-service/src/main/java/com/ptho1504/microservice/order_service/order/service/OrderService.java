package com.ptho1504.microservice.order_service.order.service;

import java.util.List;

import com.ptho1504.microservice.order_service.order.dto.request.ChangeStatusOrderRequest;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderRequest;
import com.ptho1504.microservice.order_service.order.dto.request.PaginationRequest;
import com.ptho1504.microservice.order_service.order.dto.response.OrderResponse;
import com.ptho1504.microservice.order_service.order.dto.response.PageResult;
import com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest orderRequest);

    PageResult<OrderResponse> findAll(PaginationRequest requestFindAll);

    OrderResponse findByOrderId(Integer orderId);

    OrderResponse cancelOrderById(ChangeStatusOrderRequest request);

    Boolean checkOrderInOrders(Integer customerId, Integer orderId);

    PageResult<OrderResponse> findAllOrdersByCustomerId(PaginationRequest request, Integer customerId);

    PageResult<OrderResponse> findAllOrdersByMe(PaginationRequest requestFindAll, Integer userId);

    Object test(OrderConfirmationRequest request);

}
