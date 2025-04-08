package com.microservices.upload_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserFromHeader {
        private String email;
        private Integer roleId;
        private Integer userId;
}