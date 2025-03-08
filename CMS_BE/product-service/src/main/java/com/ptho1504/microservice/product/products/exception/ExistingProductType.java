package com.ptho1504.microservice.product.products.exception;

public class ExistingProductType extends RuntimeException {
    private final int errorCode;

    public ExistingProductType(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
