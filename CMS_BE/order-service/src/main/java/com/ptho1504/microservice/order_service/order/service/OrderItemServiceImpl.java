package com.ptho1504.microservice.order_service.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.order_service.order.client.ProductClient;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderItemRequest;
import com.ptho1504.microservice.order_service.order.dto.request.UpdateOrderItemRequest;
import com.ptho1504.microservice.order_service.order.exception.OrderItemNotFound;
import com.ptho1504.microservice.order_service.order.model.Order;
import com.ptho1504.microservice.order_service.order.model.OrderItem;
import com.ptho1504.microservice.order_service.order.repository.OrderItemRepository;
import com.ptho1504.microservices.order_service.product.ProductRequest;
import com.ptho1504.microservices.order_service.product.ProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final Logger logger = LoggerFactory.getLogger(OrderItemService.class);
    private final OrderItemRepository repository;

    @Override
    public void deleteById(Integer orderId) {
        try {
            Optional<OrderItem> optional = repository.findById(orderId);
            if (optional.isEmpty()) {
                throw new OrderItemNotFound(50001, "Order Item Not found");
            }

            OrderItem orderItem = optional.get();

            repository.delete(orderItem);

        } catch (Exception e) {
            this.logger.error("Some thing wrong", e.getMessage());
            throw e;
        }

    }

    @Override
    public Optional<OrderItem> findById(Integer orderId) {
        try {
            return repository.findById(orderId);
        } catch (Exception e) {
            this.logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderItem createOrderItem(CreateOrderItemRequest request) {
        try {

            return null;
        } catch (Exception e) {
            this.logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderItem updateOrderItem(Integer orderId, UpdateOrderItemRequest request) {
        try {
            Optional<OrderItem> optional = repository.findById(orderId);
            if (optional.isEmpty()) {
                throw new OrderItemNotFound(50001, "Order Item Not found");
            }

            return null;

        } catch (Exception e) {
            this.logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        try {

            return repository.save(orderItem);

        } catch (Exception e) {
            this.logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

}
