package com.ptho1504.microservice.order_service.order.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservice.order_service.order._enum.OrderStatus;
import com.ptho1504.microservice.order_service.order.annotation.UserRequestHeader;
import com.ptho1504.microservice.order_service.order.dto.UserFromHeader;
import com.ptho1504.microservice.order_service.order.dto.request.ChangeStatusOrderRequest;
import com.ptho1504.microservice.order_service.order.dto.request.CreateOrderRequest;
import com.ptho1504.microservice.order_service.order.dto.request.PaginationRequest;
import com.ptho1504.microservice.order_service.order.dto.response.ApiResponse;
import com.ptho1504.microservice.order_service.order.dto.response.OrderResponse;
import com.ptho1504.microservice.order_service.order.dto.response.PageResult;
import com.ptho1504.microservice.order_service.order.dto.response.ResponseUtil;
import com.ptho1504.microservice.order_service.order.kafka.OrderConfirmationRequest;
import com.ptho1504.microservice.order_service.order.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
        private final OrderService orderService;

        @PostMapping()
        public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@UserRequestHeader UserFromHeader user,
                        @Valid @RequestBody CreateOrderRequest createRequest,
                        HttpServletRequest request) {

                createRequest.setUserId(user.getUserId());
                OrderResponse response = this.orderService.createOrder(createRequest);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "Create order Successfully",
                                                request.getRequestURI()));
        }

        @GetMapping()
        public ResponseEntity<ApiResponse<PageResult<OrderResponse>>> findAll(
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "5") Integer size,
                        @RequestParam(required = false, defaultValue = "id") String sortField,
                        @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                        HttpServletRequest request) {
                PaginationRequest requestFindAll = new PaginationRequest(page, size, sortField, direction);

                PageResult<OrderResponse> response = this.orderService.findAll(requestFindAll);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findAll order Successfully",
                                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<OrderResponse>> findOrderById(
                        @PathVariable("id") Integer orderId,
                        HttpServletRequest request) {

                OrderResponse response = this.orderService.findByOrderId(orderId);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findAll order Successfully",
                                                request.getRequestURI()));
        }

        @GetMapping("/{id}/cancel")
        public ResponseEntity<ApiResponse<OrderResponse>> cancelOrderById(
                        @UserRequestHeader UserFromHeader user,
                        @PathVariable("id") Integer orderId,
                        HttpServletRequest request) {

                ChangeStatusOrderRequest changeStatusOrderRequest = new ChangeStatusOrderRequest(user.getUserId(),
                                orderId,
                                OrderStatus.CANCEL);
                OrderResponse response = this.orderService.cancelOrderById(changeStatusOrderRequest);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findAll order Successfully",
                                                request.getRequestURI()));
        }

        @GetMapping("/customers/{id}")
        public ResponseEntity<ApiResponse<PageResult<OrderResponse>>> findAllOrdersByCustomerId(
                        @PathVariable("id") Integer customerId,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "5") Integer size,
                        @RequestParam(required = false, defaultValue = "id") String sortField,
                        @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                        HttpServletRequest request) {
                PaginationRequest requestFindAll = new PaginationRequest(page, size, sortField, direction);

                PageResult<OrderResponse> response = this.orderService.findAllOrdersByCustomerId(requestFindAll,
                                customerId);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findAllOrdersByCustomerId Successfully",
                                                request.getRequestURI()));
        }

        @GetMapping("/customers/me")
        public ResponseEntity<ApiResponse<PageResult<OrderResponse>>> findAllOrdersByMe(
                        @UserRequestHeader UserFromHeader user,
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "5") Integer size,
                        @RequestParam(required = false, defaultValue = "id") String sortField,
                        @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                        HttpServletRequest request) {
                PaginationRequest requestFindAll = new PaginationRequest(page, size, sortField, direction);

                PageResult<OrderResponse> response = this.orderService.findAllOrdersByMe(requestFindAll,
                                user.getUserId());
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findAllOrdersByMe Successfully",
                                                request.getRequestURI()));
        }

        @PostMapping("/test")
        public Object test(@RequestBody OrderConfirmationRequest request) {
                return ResponseEntity.ok(this.orderService.test(request));
        }
}
