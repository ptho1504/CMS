package com.microservice.cms.customer_service.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    // private final AddressService addressService;

    // @PostMapping("/")
    // public ResponseEntity<ApiResponse<String>> createAddress(@UserRequestHeader
    // UserFromHeader user,
    // HttpServletRequest request,
    // @RequestBody CreateAddressRequest createAddressRequest) {
    // // System.out.println(user);
    // String response = addressService.createAddress(user.getUserId(),
    // createAddressRequest);
    // return ResponseEntity
    // .ok(ResponseUtil.success(response, "User created sucessfully",
    // request.getRequestURI()));
    // }
}
