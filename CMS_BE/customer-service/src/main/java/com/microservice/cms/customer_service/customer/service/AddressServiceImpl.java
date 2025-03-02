package com.microservice.cms.customer_service.customer.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.exception.CustomerNotFound;
import com.microservice.cms.customer_service.customer.mapper.AddressMapper;
import com.microservice.cms.customer_service.customer.model.Address;
import com.microservice.cms.customer_service.customer.model.Customer;
import com.microservice.cms.customer_service.customer.repository.AddressRepository;
import com.microservice.cms.customer_service.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository repository;
    private final CustomerService customerService;
    private final AddressMapper mapper;

    @Override
    public String createAddress(CreateAddressRequest request) {
        try {
            // Integer customerId = request.customerId();
            // // Check CustomerId

            // Optional<Customer> customOptional =
            // this.customerService.findById(customerId);

            // if (customOptional.isEmpty()) {
            // // throw Exception
            // throw new CustomerNotFound(30001, "Not Found Customer By Id");
            // }

            // Customer customer = customOptional.get();
            // Address address = mapper.toAddress(request);
            // address.setCustomer(customer);
            // repository.save(address);

            return "Address created successfully";
        } catch (Exception e) {
            logger.error("An error occurred while save the user", e.getMessage());
            throw e;
        }
    }

}
