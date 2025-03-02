package com.ptho1504.microservices.auth_service.service;

import com.ptho1504.microservices.auth_service.dto.request.CustomerRegisterRequest;
import com.ptho1504.microservices.auth_service.dto.request.LoginRequest;
import com.ptho1504.microservices.auth_service.dto.request.RegisterRequest;
import com.ptho1504.microservices.auth_service.model.User;

public interface AuthService {
    User findUserById(Integer id);

    User findUserByEmail(String email);

    String saveUser(RegisterRequest requestRegister);

    String saveCustomers(CustomerRegisterRequest requestRegister);

    String login(LoginRequest request);

}
