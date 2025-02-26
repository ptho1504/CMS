package com.ptho1504.microservices.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.ApiResponse;
import com.ptho1504.microservices.user_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;
// import com.ptho1504.microservices.user_service.service.UserGrpcService;
import com.ptho1504.microservices.user_service.service.UserGrpcServiceImpl;
import com.ptho1504.microservices.user_service.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // private final UserService userService;
    private final UserGrpcServiceImpl userService;

    @GetMapping("hello-world")
    public void helloWorld() {
        System.out.println("Hello world");
    }

    // @PostMapping()
    // public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest createUserRequest,
    //         HttpServletRequest request) {
    //     UserResponse newUserResponse = this.userService.createUser(createUserRequest);
    //     return ResponseEntity
    //             .ok(ResponseUtil.success(newUserResponse, "User created sucessfully", request.getRequestURI()));
    // }
}
