package com.ptho1504.microservice.product.products.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors) {
}
