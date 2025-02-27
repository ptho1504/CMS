package com.ptho1504.microservices.auth_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.client.UserClient;
import com.ptho1504.microservices.auth_service.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    private final UserClient userClient;

    public Object saveUser() {
        return null;
    }

    @Override
    public User findUserById(Integer id) {
        return this.userClient.findUserById(id);
    }

}
