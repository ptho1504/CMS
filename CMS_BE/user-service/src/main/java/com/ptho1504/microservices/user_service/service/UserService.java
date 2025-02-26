package com.ptho1504.microservices.user_service.service;

import org.springframework.stereotype.Service;

import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;

public interface UserService {
    User findById(Integer id);

    UserResponse createUser(CreateUserRequest createUserRequest);
}
