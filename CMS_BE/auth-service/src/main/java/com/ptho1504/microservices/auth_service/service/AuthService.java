package com.ptho1504.microservices.auth_service.service;

import com.ptho1504.microservices.auth_service.model.User;

public interface AuthService {
    User findUserById(Integer id);
}
