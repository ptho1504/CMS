package com.ptho1504.microservices.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserFromHeader {
        private String email;
        private Integer roleId;
}
