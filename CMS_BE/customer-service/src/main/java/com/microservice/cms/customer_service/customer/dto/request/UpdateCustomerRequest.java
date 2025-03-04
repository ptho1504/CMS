package com.microservice.cms.customer_service.customer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
        @NotNull(message = "Name is required") @Size(min = 3, message = "Name must be at least 3 characters long") String name,
        @NotNull(message = "Phone is required") @Size(min = 3, message = "Phone must be at least 3 characters long") String phone

) {

}
