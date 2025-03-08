package com.ptho1504.microservice.product.products.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateProductRequest(
                @NotNull(message = "Name is required") String name,
                @NotNull(message = "Description is required") String description,
                @NotNull(message = "Price is required") @Min(value = 0, message = "Price is greater than 0") Double price,
                @NotNull(message = "stockQuantity is required") @Min(value = 0, message = "stockQuantity is greater than 0") Integer stockQuantity,
                @NotNull(message = "productType is required") Integer productTypeId) {
}
