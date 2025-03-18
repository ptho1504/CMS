package com.ptho1504.microservices.payment_service.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservices.payment_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.payment_service.dto.response.ApiResponse;
import com.ptho1504.microservices.payment_service.dto.response.PageResult;
import com.ptho1504.microservices.payment_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.payment_service.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/test")
    public String testing() {
        return "Testing";
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
}
