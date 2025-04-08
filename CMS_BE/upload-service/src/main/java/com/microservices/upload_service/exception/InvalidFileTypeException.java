package com.microservices.upload_service.exception;

public class InvalidFileTypeException extends RuntimeException {
    private final int errorCode;

    public InvalidFileTypeException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
