package com.microservice.cms.customer_service.customer.exception;

public class CustomerNotFound extends RuntimeException {
    private final int errorCode;

    public CustomerNotFound(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
