package com.microservice.cms.customer_service.customer.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors) {
}
