package com.microservice.cms.customer_service.customer.exception;

public class AddressNotFound extends RuntimeException {
    private final int errorCode;

    public AddressNotFound(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
