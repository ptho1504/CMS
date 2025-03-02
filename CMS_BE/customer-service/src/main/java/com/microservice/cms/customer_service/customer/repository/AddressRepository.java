package com.microservice.cms.customer_service.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.cms.customer_service.customer.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
