package com.microservice.cms.customer_service.customer.exception;

public class AddressNotFoundException extends RuntimeException {
    private final int errorCode;

    public AddressNotFoundException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
