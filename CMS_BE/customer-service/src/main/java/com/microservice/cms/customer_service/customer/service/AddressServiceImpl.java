package com.microservice.cms.customer_service.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.response.AddressResponse;
import com.microservice.cms.customer_service.customer.exception.AddressNotFoundException;
import com.microservice.cms.customer_service.customer.mapper.AddressMapper;
import com.microservice.cms.customer_service.customer.model.Address;
import com.microservice.cms.customer_service.customer.model.Customer;
import com.microservice.cms.customer_service.customer.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository repository;
    private final AddressMapper mapper;

    @Override
    public AddressResponse createAddress(Customer customer, CreateAddressRequest request) {
        try {

            Address address = mapper.toAddress(request);

            address.setIsDefault(true);

            // ! Handle Set All default address of this customer is False

            address.setCustomer(customer);
            Address savedAddress = repository.save(address);

            return this.mapper.toAddressResponse(savedAddress);

        } catch (Exception e) {
            logger.error("An error occurred while save the user", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AddressResponse> findAllByCustomerId(Integer customerId) {
        try {

            List<Address> address = this.repository.findByCustomerId(customerId);

            return address.stream().map(mapper::toAddressResponse).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("An error occurred while findAllByCustomerId", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Address> findById(Integer id) {
        try {

            return repository.findById(id);

        } catch (Exception e) {
            logger.error("An error occurred while findAllByCustomerId", e.getMessage());
            throw e;
        }
    }

    @Override
    public AddressResponse saveAddress(Address savedAdress) {
        try {

            return mapper.toAddressResponse(repository.save(savedAdress));

        } catch (Exception e) {
            logger.error("An error occurred while saveAddress", e.getMessage());
            throw e;
        }
    }

    @Override
    public String deleteAddress(Address address) {
        try {
            this.repository.delete(address);
            return "Deleted Address Successfully";

        } catch (Exception e) {
            logger.error("An error occurred while deleteAddress", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void unSetDefaultStateAddressByCustomerId(Integer CustomerId) {
        try {
            this.repository.unsetDefaultAddressByCustomerId(CustomerId);

        } catch (Exception e) {
            logger.error("An error occurred while changeDefaultStateAddressByCustomerId", e.getMessage());
            throw e;
        }

    }

    @Override
    public AddressResponse findDefaultAddressByCustomerId(Integer customerId) {
        try {
            Optional<Address> addressOptional = this.repository.findByCustomerIdAndIsDefault(customerId, true);

            if (addressOptional.isEmpty()) {
                throw new AddressNotFoundException(40004, "Default Address not found with customerId");
            }

            Address address = addressOptional.get();

            return mapper.toAddressResponse(address);

        } catch (Exception e) {
            logger.error("An error occurred while findDefaultAddressByCustomerId", e.getMessage());
            throw e;
        }
    }

}
