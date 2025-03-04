package com.microservice.cms.customer_service.customer.controller;

import java.util.List;

import org.apache.http.HttpRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.cms.customer_service.annotation.UserRequestHeader;
import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.request.PaginationRequest;
import com.microservice.cms.customer_service.customer.dto.request.UpdateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.request.UpdateCustomerRequest;
import com.microservice.cms.customer_service.customer.dto.response.AddressResponse;
import com.microservice.cms.customer_service.customer.dto.response.ApiResponse;
import com.microservice.cms.customer_service.customer.dto.response.CustomerResponse;
import com.microservice.cms.customer_service.customer.dto.response.PageResult;
import com.microservice.cms.customer_service.customer.dto.response.ResponseUtil;
import com.microservice.cms.customer_service.customer.service.CustomerService;
import com.microservice.cms.customer_service.user.dto.UserFromHeader;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

        // page=0&size=2&sortField=email&direction=DESC
        @GetMapping()
        public ResponseEntity<ApiResponse<PageResult<CustomerResponse>>> findAll(
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "5") Integer size,
                        @RequestParam(required = false, defaultValue = "id") String sortField,
                        @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                        HttpServletRequest request) {
                PaginationRequest requestFindAll = new PaginationRequest(page, size, sortField, direction);
                PageResult<CustomerResponse> response = this.customerService.findAll(requestFindAll);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "Find All customer sucessfully",
                                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<CustomerResponse>> findCustomerByCustomerId(
                        @PathVariable("id") Integer customerId,
                        HttpServletRequest request) {
                CustomerResponse response = this.customerService.findCustomerByCustomerId(customerId);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findCustomerByCustomerId sucessfully",
                                                request.getRequestURI()));
        }

        @GetMapping("/{id}/address")
        public ResponseEntity<ApiResponse<List<AddressResponse>>> findAllAddressByCustomerId(
                        @PathVariable("id") Integer customerId,
                        HttpServletRequest request) {
                List<AddressResponse> response = this.customerService.findAddressByCustomerId(customerId);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findAddressByCustomerId sucessfully",
                                                request.getRequestURI()));
        }

        @PostMapping("/address")
        public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
                        @UserRequestHeader UserFromHeader user,
                        @RequestBody CreateAddressRequest createAddressRequest,
                        HttpServletRequest request) {
                AddressResponse response = this.customerService.createAddress(user.getUserId(),
                                createAddressRequest);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "findAddressByCustomerId sucessfully",
                                                request.getRequestURI()));
        }

        @PutMapping("/address/{addressId}")
        public ResponseEntity<ApiResponse<AddressResponse>> updateAddressByAddressId(
                        @UserRequestHeader UserFromHeader user,
                        @PathVariable("addressId") Integer addressId,
                        @Valid @RequestBody UpdateAddressRequest updateAddressRequest,
                        HttpServletRequest request) {
                AddressResponse response = this.customerService.updateAddressByAddressId(user.getUserId(),
                                addressId,
                                updateAddressRequest);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "updateCustomerByCustomerId sucessfully",
                                                request.getRequestURI()));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomerByCustomerId(
                        @PathVariable("id") Integer customerId,
                        @Valid @RequestBody UpdateCustomerRequest updatedCustomer,
                        HttpServletRequest request) {
                CustomerResponse response = this.customerService.updateCustomerByCustomerId(customerId,
                                updatedCustomer);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "updateCustomerByCustomerId sucessfully",
                                                request.getRequestURI()));
        }

        @PutMapping()
        public ResponseEntity<ApiResponse<CustomerResponse>> updateMyCustomerByCustomerId(
                        @UserRequestHeader UserFromHeader user,
                        @Valid @RequestBody UpdateCustomerRequest updatedCustomer,
                        HttpServletRequest request) {
                CustomerResponse response = this.customerService.updateMyCustomerByCustomerId(user.getUserId(),
                                updatedCustomer);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "updateMyCustomerByCustomerId sucessfully",
                                                request.getRequestURI()));
        }

        @DeleteMapping("/address/{addressId}")
        public ResponseEntity<ApiResponse<String>> deleteAddressByAddressId(
                        @UserRequestHeader UserFromHeader user,
                        @PathVariable("addressId") Integer addressId,
                        HttpServletRequest request) {
                String response = this.customerService.deleteAddressByAddressId(user.getUserId(),
                                addressId);
                return ResponseEntity
                                .ok(ResponseUtil.success(response, "updateCustomerByCustomerId sucessfully",
                                                request.getRequestURI()));
        }
}
