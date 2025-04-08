package com.microservices.upload_service.exception;

public class NotFoundFileException extends RuntimeException {
    private final int errorCode;

    public NotFoundFileException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
