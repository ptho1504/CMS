package com.microserver.cms.gateway_service.dto.response;

import lombok.Builder;

@Builder
public record UserAndRoleId(
                String email,
                int roleId) {

}
