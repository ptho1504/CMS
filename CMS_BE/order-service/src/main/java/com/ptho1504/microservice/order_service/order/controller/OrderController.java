package com.ptho1504.microservice.order_service.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservice.order_service.order.annotation.UserRequestHeader;
import com.ptho1504.microservice.order_service.order.dto.UserFromHeader;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderRequest;
import com.ptho1504.microservice.order_service.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public Object createOrder(@UserRequestHeader UserFromHeader user, @Valid @RequestBody CreateOrderRequest request) {
        System.out.println(user);
        request.setUserId(user.getUserId());
        return this.orderService.createOrder(request);
    }

}
