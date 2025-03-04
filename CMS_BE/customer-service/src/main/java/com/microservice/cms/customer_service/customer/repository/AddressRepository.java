package com.microservice.cms.customer_service.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservice.cms.customer_service.customer.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByCustomerId(Integer Id);

    @Modifying
    @Query(value = "UPDATE address SET is_default = false WHERE customer_id = :customerId", nativeQuery = true)
    void unsetDefaultAddressByCustomerId(@Param("customerId") Integer customerId);
}
