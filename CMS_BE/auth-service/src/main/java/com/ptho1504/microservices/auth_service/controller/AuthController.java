package com.ptho1504.microservices.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservices.auth_service.dto.response.ApiResponse;
import com.ptho1504.microservices.auth_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.auth_service.model.User;
import com.ptho1504.microservices.auth_service.service.AuthService;
import com.ptho1504.microservices.auth_service.service.AuthServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // @PostMapping("/register")
    // public Object register(@RequestBody ){

    // }

    @GetMapping("/test/user/{id}")
    public ResponseEntity<ApiResponse<User>> testingGrpcFindUserByID(@PathVariable("id") Integer id,
            HttpServletRequest request) {
        User user = this.authService.findUserById(id);
        return ResponseEntity.ok(ResponseUtil.success(user, "Find User Successfully", request.getRequestURI()));
    }
}
