package com.microservices.upload_service.client;

import com.microservices.upload_service.dto.response.UserAndRoleId;

public interface AuthClient {
    public boolean validateToken(String token);

    public UserAndRoleId extractEmailAndRoleId(String token);
}
