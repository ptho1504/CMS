package com.ptho1504.microservices.auth_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.client.UserClient;
import com.ptho1504.microservices.auth_service.dto.request.RegisterRequest;
import com.ptho1504.microservices.auth_service.exception.PasswordNotMatch;
import com.ptho1504.microservices.auth_service.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    private final UserClient userClient;

    @Override
    public String saveUser(RegisterRequest request) {
        try {
            // Validate Password Confirmation

            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new PasswordNotMatch(20002, "Password not  match");
            }

            request.setPassword(passwordEncoder.encode(request.getPassword()));
            String message = userClient.saveUser(request).getMessage();
            return message;

        } catch (Exception e) {
            logger.error("An error occurred while saveUser", e.getMessage());
            throw e;
        }
    }

    @Override
    public User findUserById(Integer id) {
        return this.userClient.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userClient.findUserByEmail(email);
    }

}
