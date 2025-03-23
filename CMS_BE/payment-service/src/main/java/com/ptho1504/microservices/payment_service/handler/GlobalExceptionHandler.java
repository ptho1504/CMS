package com.ptho1504.microservices.payment_service.handler;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ptho1504.microservices.payment_service.dto.response.ApiResponse;
import com.ptho1504.microservices.payment_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.payment_service.exception.OrderExisted;
import com.ptho1504.microservices.payment_service.exception.PaymentNotFound;
import com.ptho1504.microservices.payment_service.exception.PaymentPermission;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(OrderExisted.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Object> handle(OrderExisted e, HttpServletRequest request) {
        logger.error("{}", e.getMessage());
        return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(), e.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(PaymentNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handle(PaymentNotFound e, HttpServletRequest request) {
        logger.error("{}", e.getMessage());
        return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(), e.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(PaymentPermission.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handle(PaymentPermission e, HttpServletRequest request) {
        logger.error("{}", e.getMessage());
        return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(), e.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }
}