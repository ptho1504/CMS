package com.ptho1504.microservices.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservices.auth_service.dto.request.LoginRequest;
import com.ptho1504.microservices.auth_service.dto.request.RegisterRequest;
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

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.saveUser(request);
    }

    @PostMapping("/register/customers")
    public String registerCustomers(@RequestBody RegisterRequest request) {
        return authService.saveUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // Request for testing
    @GetMapping("/test/user/email/{email}")
    public ResponseEntity<ApiResponse<User>> testingGrpcFindUserByID(@PathVariable("email") String email,
            HttpServletRequest request) {
        User user = this.authService.findUserByEmail(email);
        return ResponseEntity.ok(ResponseUtil.success(user, "Find User Successfully", request.getRequestURI()));
    }

    @GetMapping("/hello-world")
    public String helloWorld(
            HttpServletRequest request) {
        return "Hello World";
    }

}
