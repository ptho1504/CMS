package com.ptho1504.microservice.order_service.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservice.order_service.order.service.OrderItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-items")
public class OrderItemController {
    private final OrderItemService orederItemService;
}
