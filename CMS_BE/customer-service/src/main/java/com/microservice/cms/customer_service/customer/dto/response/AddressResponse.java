package com.microservice.cms.customer_service.customer.dto.response;

import lombok.Builder;

@Builder
public record AddressResponse(
                Integer id,
                String street,
                String province,
                String ward,
                Boolean isDefault) {

}
