package com.ptho1504.microservices.auth_service.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {

    private Integer id;

    private String email;

    private String username;

    private Boolean enabled;

    private Integer roleId;
}
