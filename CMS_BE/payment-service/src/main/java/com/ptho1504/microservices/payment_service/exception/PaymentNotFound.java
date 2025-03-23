package com.ptho1504.microservices.payment_service.exception;

public class PaymentNotFound extends RuntimeException {

    private final int errorCode;

    public PaymentNotFound(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}