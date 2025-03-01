package com.microserver.cms.gateway_service.client;

import com.microserver.cms.gateway_service.dto.response.UserAndRoleId;

public interface AuthClient {
    public boolean validateToken(String token);

    public UserAndRoleId extractEmailAndRoleId(String token);
}
