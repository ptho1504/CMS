package com.ptho1504.microservices.user_service.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

public record UserResponse(
        Integer id,
        String username,
        String email,
        Date createdAt,
        LocalDateTime updatedAt,
        Boolean enabled,
        String password) {

}
