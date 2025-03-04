package com.microservice.cms.customer_service.customer.service;

import java.util.List;
import java.util.Optional;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.request.PaginationRequest;
import com.microservice.cms.customer_service.customer.dto.request.UpdateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.request.UpdateCustomerRequest;
import com.microservice.cms.customer_service.customer.dto.response.AddressResponse;
import com.microservice.cms.customer_service.customer.dto.response.CustomerResponse;
import com.microservice.cms.customer_service.customer.dto.response.PageResult;
import com.microservice.cms.customer_service.customer.model.Customer;

public interface CustomerService {
    Optional<Customer> findById(Integer id);

    Optional<Customer> findByUserId(Integer userId);

    PageResult<CustomerResponse> findAll(PaginationRequest request);

    CustomerResponse findCustomerByCustomerId(Integer Id);

    CustomerResponse updateCustomerByCustomerId(Integer Id, UpdateCustomerRequest updatedCustomer);

    CustomerResponse updateMyCustomerByCustomerId(Integer userId, UpdateCustomerRequest updatedCustomer);

    AddressResponse updateAddressByAddressId(Integer userId, Integer addressId,
            UpdateAddressRequest updateAddressRequest);

    List<AddressResponse> findAddressByCustomerId(Integer customerId);

    AddressResponse createAddress(Integer userId, CreateAddressRequest createAddressRequest);

    String deleteAddressByAddressId(Integer userId, Integer addressId);
}
