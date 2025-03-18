package com.ptho1504.microservices.auth_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {
        @NotNull(message = "Email is required")
        private String email;

        @NotNull(message = "Username is required")
        @Size(min = 3, message = "Username must be at least 3 characters long")
        private String username;

        @NotNull(message = "Password is required")
        @Size(min = 3, message = "Password must be at least 3 characters long")
        private String password;

        @NotNull(message = "Confirm password is required")
        @Size(min = 3, message = "Confirm password must be at least 3 characters long")
        private String confirmPassword;
}
