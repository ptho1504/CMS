package com.ptho1504.microservices.user_service.service;

import java.util.Optional;

import com.ptho1504.microservices.user_service.dto.UserFromHeader;
import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.user_service.dto.request.UpdateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.PageResult;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;

public interface UserService {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    String createUser(CreateUserRequest createUserRequest);

    PageResult<UserResponse> findAll(PaginationRequest request);

    UserResponse findInformationByUserId(Integer id);

    UserResponse updateUserById(Integer id, UpdateUserRequest updatedUser);

    UserResponse updateMyInformation(UserFromHeader user, UpdateUserRequest updatedUser);
}
