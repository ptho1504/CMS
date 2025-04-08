package com.microservices.upload_service.dto.response;

import lombok.Builder;

@Builder
public record UserAndRoleId(
                String email,
                int roleId,
                int userId) {

}
