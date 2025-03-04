package com.microservice.cms.customer_service.customer.dto.response;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.microservice.cms.customer_service.customer.model.Address;

import lombok.Builder;

@Builder
public record CustomerResponse(
                Integer id,
                String name,
                String phone,
                Date createdAt,
                LocalDateTime updatedAt,
                List<Address> addresses) {

}
