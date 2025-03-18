package com.microservice.cms.customer_service.customer.mapper;

import org.springframework.stereotype.Component;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.response.AddressResponse;
import com.microservice.cms.customer_service.customer.model.Address;

@Component
public class AddressMapper {
    public Address toAddress(CreateAddressRequest request) {
        return Address.builder()
                .province(request.province())
                .street(request.street())
                .ward(request.ward())
                .build();
    }

    public AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .ward(address.getWard())
                .province(address.getProvince())
                .isDefault(address.getIsDefault())
                .build();
    }
}
