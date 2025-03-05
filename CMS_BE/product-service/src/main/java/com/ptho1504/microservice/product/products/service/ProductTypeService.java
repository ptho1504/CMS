package com.ptho1504.microservice.product.products.service;

import java.util.List;
import java.util.Optional;

import com.ptho1504.microservice.product.products.dto.request.CreateProductTypeRequest;
import com.ptho1504.microservice.product.products.dto.request.UpdateProductType;
import com.ptho1504.microservice.product.products.dto.response.ProductTypeResponse;
import com.ptho1504.microservice.product.products.model.ProductType;

import jakarta.validation.Valid;

public interface ProductTypeService {

    String createProductType(CreateProductTypeRequest createRequest);

    String updateProductType(Integer producTypeId, UpdateProductType updateRequest);

    Optional<ProductType> findById(Integer id);

    Optional<ProductType> findByName(String name);

    List<ProductTypeResponse> findAll();

    String deleteProductTypeById(Integer producTypeId);

}
