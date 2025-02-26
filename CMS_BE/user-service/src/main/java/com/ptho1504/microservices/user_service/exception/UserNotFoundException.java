package com.ptho1504.microservices.user_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class UserNotFoundException extends RuntimeException {
    private final int errorCode;

    public UserNotFoundException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
