package com.microservice.cms.customer_service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserFromHeader {
        private String email;
        private Integer roleId;
        private Integer userId;
}
