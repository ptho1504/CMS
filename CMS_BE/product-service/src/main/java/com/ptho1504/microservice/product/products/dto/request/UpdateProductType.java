package com.ptho1504.microservice.product.products.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductType(
        @NotNull(message = "Name of product type is required") @Size(min = 3, message = "Min character at least 3 characters") String name) {

}
