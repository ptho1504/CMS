package com.ptho1504.microservices.user_service.mapper;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;

@Component
public class UserMapper {
    public User toUser(CreateUserRequest userRequest) {
        return User.builder()
                .id(userRequest.id())
                .username(userRequest.username())
                .email(userRequest.email())
                .createdAt(new Date())
                .updatedAt(LocalDateTime.now())
                .password(userRequest.password())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getEnabled(),
                user.getPassword());
    }
}
