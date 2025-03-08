package com.ptho1504.microservice.order_service.order.exception;

public class OrderItemNotFound extends RuntimeException {
    private final int errorCode;

    public OrderItemNotFound(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
