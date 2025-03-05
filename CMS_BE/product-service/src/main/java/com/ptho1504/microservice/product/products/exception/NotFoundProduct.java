package com.ptho1504.microservice.product.products.exception;

public class NotFoundProduct extends RuntimeException {
    private final int errorCode;

    public NotFoundProduct(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
