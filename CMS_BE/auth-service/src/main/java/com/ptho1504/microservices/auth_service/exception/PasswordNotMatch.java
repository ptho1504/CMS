package com.ptho1504.microservices.auth_service.exception;

public class PasswordNotMatch extends RuntimeException {
    private final int errorCode;

    public PasswordNotMatch(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
