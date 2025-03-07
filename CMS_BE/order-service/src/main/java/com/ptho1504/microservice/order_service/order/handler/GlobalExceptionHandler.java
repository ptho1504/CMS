package com.ptho1504.microservice.order_service.order.handler;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler(NotFoundProductType.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public ApiResponse<Object> handle(NotFoundProductType e, HttpServletRequest request) {
    //     logger.error("{}", e.getMessage());
    //     return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(), e.getErrorCode(),
    //             request.getRequestURI());
    // }

    // @ExceptionHandler(ExistingProductType.class)
    // @ResponseStatus(HttpStatus.CONFLICT)
    // public ApiResponse<Object> handle(ExistingProductType e, HttpServletRequest request) {
    //     logger.error("{}", e.getMessage());
    //     return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(), e.getErrorCode(),
    //             request.getRequestURI());
    // }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        logger.error("{}", e.getMessage());
        var errors = new HashMap<String, String>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }
}