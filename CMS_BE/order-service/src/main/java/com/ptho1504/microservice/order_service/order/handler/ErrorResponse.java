package com.ptho1504.microservice.order_service.order.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors) {
}
