package com.ptho1504.microservice.product.products.service;

import java.util.Optional;

import com.ptho1504.microservice.product.products.dto.request.CreateProductRequest;
import com.ptho1504.microservice.product.products.dto.request.PaginationRequest;
import com.ptho1504.microservice.product.products.dto.request.UpdateProductRequest;
import com.ptho1504.microservice.product.products.dto.response.PageResult;
import com.ptho1504.microservice.product.products.dto.response.ProductRepsonse;
import com.ptho1504.microservice.product.products.model.Product;

public interface ProductService {

    ProductRepsonse createProduct(CreateProductRequest createRequest);

    PageResult<ProductRepsonse> findAll(PaginationRequest requestFindAll);

    ProductRepsonse findByProductId(Integer id);

    Optional<Product> findById(Integer id);

    ProductRepsonse updateProduct(Integer productId, UpdateProductRequest updateProductRequest);

}
