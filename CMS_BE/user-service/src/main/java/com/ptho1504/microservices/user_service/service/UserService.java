package com.ptho1504.microservices.user_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;

public interface UserService {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    String createUser(CreateUserRequest createUserRequest);
}
