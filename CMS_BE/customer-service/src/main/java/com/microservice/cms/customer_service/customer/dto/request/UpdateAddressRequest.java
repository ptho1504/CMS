package com.microservice.cms.customer_service.customer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(
                @NotNull(message = "Street is required") @Size(min = 3, message = "Street must be at least 3 characters long") String street,
                @NotNull(message = "Province is required") @Size(min = 3, message = "Province must be at least 3 characters long") String province,
                @NotNull(message = "Ward is required") @Size(min = 3, message = "Ward must be at least 3 characters long") String ward,
                @NotNull(message = "Ward is required") Boolean isDefault) {

}
