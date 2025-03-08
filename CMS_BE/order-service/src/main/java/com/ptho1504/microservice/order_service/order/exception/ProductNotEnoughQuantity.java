package com.ptho1504.microservice.order_service.order.exception;

public class ProductNotEnoughQuantity extends RuntimeException {
    private final int errorCode;

    public ProductNotEnoughQuantity(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
