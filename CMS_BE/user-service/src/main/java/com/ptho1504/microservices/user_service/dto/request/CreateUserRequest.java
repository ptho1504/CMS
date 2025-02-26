package com.ptho1504.microservices.user_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
                Integer id, @NotNull(message = "Email is required") String email,
                @NotNull(message = "Username is required") @Size(min = 3, message = "Username must be at least 3 characters long") String username,
                @NotNull(message = "Password is required") @Size(min = 3, message = "Password must be at least 3 characters long") String password,
                @NotNull(message = "Confirm password is required") @Size(min = 3, message = "Confirm password must be at least 3 characters long") String confirmPassword) {
}
