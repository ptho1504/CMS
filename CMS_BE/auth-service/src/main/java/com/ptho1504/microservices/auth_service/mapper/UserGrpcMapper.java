package com.ptho1504.microservices.auth_service.mapper;

import org.springframework.stereotype.Component;

import com.ptho1504.microservices.auth_service.model.User;

@Component
public class UserGrpcMapper {
    public User toUserModel(com.ptho1504.microservices.auth_service.user.User fromUser) {
        return User.builder()
                .email(fromUser.getEmail())
                .username(fromUser.getUsername())
                .id(fromUser.getId())
                .roleId(fromUser.getRoleId())
                .enabled(fromUser.getEnabled())
                .build();
    }
}