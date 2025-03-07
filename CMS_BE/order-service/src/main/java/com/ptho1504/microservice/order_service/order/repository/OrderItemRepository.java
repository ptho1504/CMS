package com.ptho1504.microservice.order_service.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptho1504.microservice.order_service.order.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
