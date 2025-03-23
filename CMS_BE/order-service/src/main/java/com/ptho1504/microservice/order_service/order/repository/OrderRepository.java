package com.ptho1504.microservice.order_service.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ptho1504.microservice.order_service.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findById(Integer orderId);

    Optional<Order> findByIdAndCustomerId(Integer orderId, Integer customId);

    List<Order> findByCustomerId(Integer customerId);

    Page<Order> findByCustomerId(Integer customerId, Pageable pageable);
}
