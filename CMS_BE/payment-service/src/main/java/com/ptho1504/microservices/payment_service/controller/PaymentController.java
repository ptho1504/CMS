package com.ptho1504.microservices.payment_service.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ptho1504.microservices.notify_service.notify.kafka.PaymentEvent;
import com.ptho1504.microservices.order_service.order.kafka.OrderConfirmationRequest;
import com.ptho1504.microservices.payment_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.payment_service.dto.response.ApiResponse;
import com.ptho1504.microservices.payment_service.dto.response.PageResult;
import com.ptho1504.microservices.payment_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.payment_service.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/test")
    public void testing(@RequestBody PaymentEvent request) {
        this.paymentService.test(request);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Object>> findAllPayments(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            HttpServletRequest request) {
        PaginationRequest requestFindAll = new PaginationRequest(page, size, sortField, direction);

        PageResult<Object> response = this.paymentService.findAll(requestFindAll);
        return ResponseEntity
                .ok(ResponseUtil.success(response, "findAll order Successfully", request.getRequestURI()));
    }

    @PostMapping("/pay-os/handle-web-hook")
    public ResponseEntity<ApiResponse<Object>> handleWebHookPayOs(
            @RequestBody ObjectNode bNode,
            HttpServletRequest request) {
        log.info("web hook {} ", bNode);
        Object response = this.paymentService.handlePayOsWebHook(bNode);
        return ResponseEntity
                .ok(ResponseUtil.success(response, "handleWebHook order Successfully", request.getRequestURI()));
    }

    @GetMapping(value = "/success")
    public String Success() {
        return "success";
    }

    @GetMapping(value = "/cancel")
    public String Cancel() {
        return "cancel";
    }
}
