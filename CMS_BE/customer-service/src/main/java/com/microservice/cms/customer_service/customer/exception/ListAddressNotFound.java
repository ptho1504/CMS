package com.microservice.cms.customer_service.customer.exception;

public class ListAddressNotFound extends RuntimeException {
    private final int errorCode;

    public ListAddressNotFound(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
