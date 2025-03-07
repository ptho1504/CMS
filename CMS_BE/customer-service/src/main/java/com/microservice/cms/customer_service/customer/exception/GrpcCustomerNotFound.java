package com.microservice.cms.customer_service.customer.exception;

public class GrpcCustomerNotFound extends RuntimeException {
    private final int errorCode;

    public GrpcCustomerNotFound(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
