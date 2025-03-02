package com.microservice.cms.customer_service.customer.controller;

import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.cms.customer_service.annotation.UserRequestHeader;
import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.service.CustomerService;
import com.microservice.cms.customer_service.user.dto.UserFromHeader;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/test")
    public String testHelloWorld() {
        return "Hello World";
    }

}
