package com.ptho1504.microservice.order_service.order.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserFromHeader {
    private String email;
    private Integer roleId;
    private Integer userId;
}
