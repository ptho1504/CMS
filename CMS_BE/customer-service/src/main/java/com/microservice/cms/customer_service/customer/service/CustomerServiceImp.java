package com.microservice.cms.customer_service.customer.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microservice.cms.customer_service.customer.dto.request.CreateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.request.PaginationRequest;
import com.microservice.cms.customer_service.customer.dto.request.UpdateAddressRequest;
import com.microservice.cms.customer_service.customer.dto.request.UpdateCustomerRequest;
import com.microservice.cms.customer_service.customer.dto.response.AddressResponse;
import com.microservice.cms.customer_service.customer.dto.response.CustomerResponse;
import com.microservice.cms.customer_service.customer.dto.response.PageResult;
import com.microservice.cms.customer_service.customer.exception.AddressNotFound;
import com.microservice.cms.customer_service.customer.exception.CustomerNotFound;
import com.microservice.cms.customer_service.customer.exception.ListAddressNotFound;
import com.microservice.cms.customer_service.customer.mapper.AddressMapper;
import com.microservice.cms.customer_service.customer.mapper.CustomerMapper;
import com.microservice.cms.customer_service.customer.model.Address;
import com.microservice.cms.customer_service.customer.model.Customer;
import com.microservice.cms.customer_service.customer.repository.CustomerRepository;
import com.microservice.cms.customer_service.customer.util.PaginationUtils;
import com.ptho1504.microservices.auth_service.customer.CreateCustomerRequest;
import com.ptho1504.microservices.auth_service.customer.CreateCustomerResponse;
import com.ptho1504.microservices.auth_service.customer.CustomerServiceGrpc.CustomerServiceImplBase;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CustomerServiceImp extends CustomerServiceImplBase implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressService addressService;

    // Grpc-server
    @Override
    public void saveCustomer(CreateCustomerRequest request, StreamObserver<CreateCustomerResponse> responseObserver) {
        logger.info("Received gRPC request for saveCustomer ID: " + request.getUserId());
        Integer userId = request.getUserId();

        Customer customer = Customer.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .userId(userId)
                .createdAt(new Date())
                .updatedAt(LocalDateTime.now())
                .build();
        customerRepository.save(customer);

        CreateCustomerResponse response = CreateCustomerResponse.newBuilder()
                .setMessage("Customer have been created")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Grpc-end

    @Override
    public Optional<Customer> findById(Integer id) {
        try {
            return this.customerRepository.findById(id);
        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Customer> findByUserId(Integer userId) {
        try {
            return this.customerRepository.findByUserId(userId);
        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public PageResult<CustomerResponse> findAll(PaginationRequest request) {
        try {
            Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(),
                    request.getDirection(),
                    request.getSortField());
            Page<Customer> entities = customerRepository.findAll(pageable);
            List<CustomerResponse> entitiesDto = entities.stream().map(customerMapper::toCustomerResponse).toList();

            return new PageResult<CustomerResponse>(
                    entitiesDto,
                    entities.getTotalPages(),
                    entities.getTotalElements(),
                    entities.getSize(),
                    entities.getNumber(),
                    entities.isEmpty());
        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public CustomerResponse findCustomerByCustomerId(Integer Id) {
        try {
            Optional<Customer> customerOptional = this.findById(Id);

            if (customerOptional.isEmpty()) {
                throw new CustomerNotFound(30001, "User Not found with id ${id}");
            }
            CustomerResponse response = this.customerMapper.toCustomerResponse(customerOptional.get());
            return response;

        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public CustomerResponse updateCustomerByCustomerId(Integer Id, UpdateCustomerRequest updatedCustomer) {
        try {
            Optional<Customer> customerOptional = this.findById(Id);

            if (customerOptional.isEmpty()) {
                throw new CustomerNotFound(30001, "Customer Not found with id ${id}");
            }

            Customer savedCustomer = customerOptional.get();

            savedCustomer.setName(updatedCustomer.name());
            savedCustomer.setUpdatedAt(LocalDateTime.now());
            savedCustomer.setPhone(updatedCustomer.phone());

            Customer customer = customerRepository.save(savedCustomer);
            CustomerResponse response = this.customerMapper.toCustomerResponse(customer);

            return response;

        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public CustomerResponse updateMyCustomerByCustomerId(Integer userId, UpdateCustomerRequest updatedCustomer) {
        try {
            Optional<Customer> customerOptional = this.findByUserId(userId);

            if (customerOptional.isEmpty()) {
                throw new CustomerNotFound(30001, "Customer Not found with id ${id}");
            }

            Customer savedCustomer = customerOptional.get();

            savedCustomer.setName(updatedCustomer.name());
            savedCustomer.setUpdatedAt(LocalDateTime.now());
            savedCustomer.setPhone(updatedCustomer.phone());

            Customer customer = customerRepository.save(savedCustomer);
            CustomerResponse response = this.customerMapper.toCustomerResponse(customer);

            return response;

        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AddressResponse> findAddressByCustomerId(Integer customerId) {
        try {
            Optional<Customer> customerOptional = this.findById(customerId);

            if (customerOptional.isEmpty()) {
                throw new CustomerNotFound(30001, "Customer Not found with id ${id}");
            }

            List<AddressResponse> addresses = this.addressService.findAllByCustomerId(customerId);

            return addresses;

        } catch (Exception e) {
            logger.error("An error occurred while findById", e.getMessage());
            throw e;
        }
    }

    @Override
    public AddressResponse createAddress(Integer userId, CreateAddressRequest createAddressRequest) {
        try {
            Optional<Customer> customerOptional = this.findByUserId(userId);

            if (customerOptional.isEmpty()) {
                throw new CustomerNotFound(30001, "Customer Not found with id ${id}");
            }

            this.addressService.unSetDefaultStateAddressByCustomerId(customerOptional.get().getId());

            AddressResponse addresses = this.addressService.createAddress(customerOptional.get(),
                    createAddressRequest);

            return addresses;

        } catch (Exception e) {
            logger.error("An error occurred while createAddress", e.getMessage());
            throw e;
        }
    }

    @Override
    public AddressResponse updateAddressByAddressId(Integer userId, Integer addressId,
            UpdateAddressRequest updateAddressRequest) {
        try {
            Optional<Customer> customerOptional = this.findByUserId(userId);

            if (customerOptional.isEmpty()) {
                throw new CustomerNotFound(30001, "Customer Not found with id");
            }

            Optional<Address> addressOptional = this.addressService.findById(addressId);

            if (addressOptional.isEmpty()) {
                throw new AddressNotFound(40001, "Address Not found with id");
            }

            List<AddressResponse> customerAddresses = this.addressService
                    .findAllByCustomerId(customerOptional.get().getId());

            if (customerAddresses.size() < 1) {
                throw new ListAddressNotFound(40002, "List Add Not found with customer id");
            }

            boolean addressExists = customerAddresses.stream()
                    .anyMatch(address -> address.id().equals(addressOptional.get().getId()));

            if (!addressExists) {
                throw new ListAddressNotFound(40003, "You can not change this address");
            }

            Address saveAddress = addressOptional.get();

            saveAddress.setProvince(updateAddressRequest.province());
            saveAddress.setStreet(updateAddressRequest.street());
            saveAddress.setWard(updateAddressRequest.ward());

            saveAddress.setDefaultAdd(true);

            return addressService.saveAddress(saveAddress);

        } catch (Exception e) {
            logger.error("An error occurred while updateAddressByAddressId", e.getMessage());
            throw e;
        }
    }

    @Override
    public String deleteAddressByAddressId(Integer userId, Integer addressId) {
        try {
            Optional<Customer> customerOptional = this.findByUserId(userId);

            if (customerOptional.isEmpty()) {
                throw new CustomerNotFound(30001, "Customer Not found with id");
            }

            Optional<Address> addressOptional = this.addressService.findById(addressId);

            if (addressOptional.isEmpty()) {
                throw new AddressNotFound(40001, "Address Not found with id");
            }

            List<AddressResponse> customerAddresses = this.addressService
                    .findAllByCustomerId(customerOptional.get().getId());

            if (customerAddresses.size() < 1) {
                throw new ListAddressNotFound(40002, "List Add Not found with customer id");
            }

            boolean addressExists = customerAddresses.stream()
                    .anyMatch(address -> address.id().equals(addressOptional.get().getId()));

            if (!addressExists) {
                throw new ListAddressNotFound(40003, "You can not change this address");
            }

            Address deleteAddress = addressOptional.get();

            return addressService.deleteAddress(deleteAddress);

        } catch (Exception e) {
            logger.error("An error occurred while deleteAddressByAddressId", e.getMessage());
            throw e;
        }
    }

}
