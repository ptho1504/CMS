package com.ptho1504.microservices.user_service.exception;

public class UserExistingException extends RuntimeException {
    private final int errorCode;

    public UserExistingException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
