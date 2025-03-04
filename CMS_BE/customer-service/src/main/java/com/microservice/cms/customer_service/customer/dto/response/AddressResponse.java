package com.microservice.cms.customer_service.customer.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record AddressResponse(
        Integer id,
        String street,
        String province,
        String ward,
        Boolean defaultAdd) {

}
