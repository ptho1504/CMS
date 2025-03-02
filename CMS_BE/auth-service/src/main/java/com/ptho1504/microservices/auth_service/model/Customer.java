package com.ptho1504.microservices.auth_service.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Customer {
    private Integer id;

    private String name;

    private String userId;

    private String phone;
}
