package com.ptho1504.microservice.order_service.order.exception;

public class NotHaveEnoughPermissionToChangeStaus extends RuntimeException {
    private final int errorCode;

    public NotHaveEnoughPermissionToChangeStaus(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
