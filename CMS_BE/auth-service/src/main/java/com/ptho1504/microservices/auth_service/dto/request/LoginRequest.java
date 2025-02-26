package com.ptho1504.microservices.auth_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record LoginRequest(
        @NotNull(message = "Email is required") String email,
        @NotNull(message = "Password is required") 
        @Size(min = 3, message = "Password must be at least 3 characters long") 
        String password) {
}
