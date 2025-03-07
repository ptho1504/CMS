package com.microservice.cms.customer_service.customer.mapper;

import org.springframework.stereotype.Component;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.response.AddressResponse;
import com.microservice.cms.customer_service.customer.dto.response.CustomerResponse;
import com.microservice.cms.customer_service.customer.model.Address;
import com.microservice.cms.customer_service.customer.model.Customer;

@Component
public class CustomerMapper {
    public CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .addresses(customer.getAddresses())
                .build();
    }

    public com.ptho1504.microservices.customer_service.customer.CustomerResponse toCustomerResponseGrpc(
            Customer customer,
            AddressResponse addressResponse) {
        return com.ptho1504.microservices.customer_service.customer.CustomerResponse.newBuilder()
                .setCustomer(
                        com.ptho1504.microservices.customer_service.customer.Customer
                                .newBuilder()
                                .setId(customer.getId())
                                .setName(customer.getName())
                                .setUserId(customer.getUserId())
                                .setPhone(customer.getPhone())
                                .setAddress(
                                        com.ptho1504.microservices.customer_service.customer.Address
                                                .newBuilder()
                                                .setIsDefault(addressResponse.isDefault())
                                                .setId(addressResponse.id())
                                                .setStreet(addressResponse.street())
                                                .setProvince(addressResponse.province())
                                                .setWard(addressResponse.province())
                                                .build())
                                .build()

                )
                .build();
    }
}
