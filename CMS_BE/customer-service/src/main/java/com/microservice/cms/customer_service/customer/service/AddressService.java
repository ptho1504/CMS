package com.microservice.cms.customer_service.customer.service;

import java.util.List;
import java.util.Optional;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.response.AddressResponse;
import com.microservice.cms.customer_service.customer.model.Address;
import com.microservice.cms.customer_service.customer.model.Customer;

public interface AddressService {
    AddressResponse createAddress(Customer customer, CreateAddressRequest request);

    List<AddressResponse> findAllByCustomerId(Integer customerId);

    AddressResponse findDefaultAddressByCustomerId(Integer customerId);

    AddressResponse saveAddress(Address savedAdress);

    String deleteAddress(Address address);

    Optional<Address> findById(Integer id);

    void unSetDefaultStateAddressByCustomerId(Integer CustomerId);
}
