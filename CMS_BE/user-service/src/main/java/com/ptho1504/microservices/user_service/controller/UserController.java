package com.ptho1504.microservices.user_service.controller;

import org.apache.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservices.user_service.annotation.UserRequestHeader;
import com.ptho1504.microservices.user_service.dto.UserFromHeader;
import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.ApiResponse;
import com.ptho1504.microservices.user_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;
import com.ptho1504.microservices.user_service.service.UserService;
import com.ptho1504.microservices.user_service.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // private final UserService userService;
    private final UserServiceImpl userService;

    @GetMapping("hello-world")
    public String helloWorld(@UserRequestHeader UserFromHeader user, HttpRequest request) {
        System.out.println(user);
        return "Hello world from User Controller";
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createUser(@Valid @RequestBody CreateUserRequest createUserRequest,
            HttpServletRequest request) {
        String newUserResponse = this.userService.createUser(createUserRequest);
        return ResponseEntity
                .ok(ResponseUtil.success(newUserResponse, "User created sucessfully", request.getRequestURI()));
    }

}
