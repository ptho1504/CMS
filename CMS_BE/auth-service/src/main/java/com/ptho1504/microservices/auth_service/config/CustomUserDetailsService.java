package com.ptho1504.microservices.auth_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ptho1504.microservices.auth_service.client.UserClient;
import com.ptho1504.microservices.auth_service.mapper.UserGrpcMapper;
import com.ptho1504.microservices.auth_service.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserClient userClient;
    // private final AuthService authService;
    private final UserGrpcMapper userGrpcMapper;
    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userClient.findUserByEmail(email);
        logger.info("Looking up user: {}", email);

        return userGrpcMapper.toCustomUserDetails(user);
    }

}
