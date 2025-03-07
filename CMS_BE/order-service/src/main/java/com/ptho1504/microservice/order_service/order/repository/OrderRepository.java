package com.ptho1504.microservice.order_service.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptho1504.microservice.order_service.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
