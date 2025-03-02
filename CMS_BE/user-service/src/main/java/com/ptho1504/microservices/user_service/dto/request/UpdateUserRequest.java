package com.ptho1504.microservices.user_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotNull(message = "Username is required") @Size(min = 3, message = "Username must be at least 3 characters long") String username) {
}
