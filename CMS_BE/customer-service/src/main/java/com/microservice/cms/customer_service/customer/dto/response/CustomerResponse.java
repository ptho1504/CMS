package com.microservice.cms.customer_service.customer.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

public record CustomerResponse(
        Integer id,
        String username,
        String email,
        Date createdAt,
        LocalDateTime updatedAt,
        Boolean enabled) {

}
