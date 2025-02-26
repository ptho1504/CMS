package com.ptho1504.microservices.auth_service.client;

import com.ptho1504.microservices.auth_service.dto.request.RegisterRequest;
import com.ptho1504.microservices.auth_service.model.User;
import com.ptho1504.microservices.auth_service.user.CreateUserResponse;

public interface UserClient {
    User findUserById(Integer Id);

    CreateUserResponse saveUser(RegisterRequest request);
}
