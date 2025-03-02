package com.ptho1504.microservices.user_service.controller;

import org.apache.http.HttpRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservices.user_service.annotation.UserRequestHeader;
import com.ptho1504.microservices.user_service.dto.UserFromHeader;
import com.ptho1504.microservices.user_service.dto.request.CreateUserRequest;
import com.ptho1504.microservices.user_service.dto.request.PaginationRequest;
import com.ptho1504.microservices.user_service.dto.request.UpdateUserRequest;
import com.ptho1504.microservices.user_service.dto.response.ApiResponse;
import com.ptho1504.microservices.user_service.dto.response.PageResult;
import com.ptho1504.microservices.user_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.user_service.dto.response.UserResponse;
import com.ptho1504.microservices.user_service.model.User;
import com.ptho1504.microservices.user_service.service.UserService;
import com.ptho1504.microservices.user_service.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    // page=0&size=2&sortField=email&direction=DESC
    @GetMapping()
    public ResponseEntity<ApiResponse<PageResult<UserResponse>>> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
            HttpServletRequest request) {
        PaginationRequest requestFindAll = new PaginationRequest(page, size, sortField, direction);

        PageResult<UserResponse> response = this.userService.findAll(requestFindAll);
        return ResponseEntity.ok(ResponseUtil.success(response, "Find All user sucessfully", request.getRequestURI()));
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findInformationByUserId(@PathVariable Integer id,
            HttpServletRequest request) {
        UserResponse response = this.userService.findInformationByUserId(id);
        return ResponseEntity
                .ok(ResponseUtil.success(response, "Find Information of user successfully", request.getRequestURI()));
    }

    @PutMapping("/information/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(@PathVariable Integer id,
            @Valid @RequestBody UpdateUserRequest requestUpdate,
            HttpServletRequest request) {
        UserResponse response = this.userService.updateUserById(id, requestUpdate);
        return ResponseEntity
                .ok(ResponseUtil.success(response, "Update Information of user successfully",
                        request.getRequestURI()));
    }

    @PutMapping("/information")
    public ResponseEntity<ApiResponse<UserResponse>> updateMyInformation(
            @UserRequestHeader UserFromHeader user,
            @Valid @RequestBody UpdateUserRequest requestUpdate,
            HttpServletRequest request) {
        UserResponse response = this.userService.updateMyInformation(user, requestUpdate);
        return ResponseEntity
                .ok(ResponseUtil.success(response, "Update My Information of user successfully",
                        request.getRequestURI()));
    }

}
