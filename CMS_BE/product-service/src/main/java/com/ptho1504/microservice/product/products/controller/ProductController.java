package com.ptho1504.microservice.product.products.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservice.product.products.dto.request.CreateProductRequest;
import com.ptho1504.microservice.product.products.dto.request.PaginationRequest;
import com.ptho1504.microservice.product.products.dto.request.UpdateProductRequest;
import com.ptho1504.microservice.product.products.dto.response.ApiResponse;
import com.ptho1504.microservice.product.products.dto.response.PageResult;
import com.ptho1504.microservice.product.products.dto.response.ProductRepsonse;
import com.ptho1504.microservice.product.products.dto.response.ResponseUtil;
import com.ptho1504.microservice.product.products.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
        private final ProductService productService;

        @PostMapping()
        public ResponseEntity<ApiResponse<ProductRepsonse>> createProduct(
                        @Valid @RequestBody CreateProductRequest createRequest,
                        HttpServletRequest request) {
                ProductRepsonse repsonse = this.productService.createProduct(createRequest);
                return ResponseEntity
                                .ok(ResponseUtil.success(repsonse, "Create Product Successfully",
                                                request.getRequestURI()));
        }

        // page=0&size=2&sortField=email&direction=DESC
        @GetMapping()
        public ResponseEntity<ApiResponse<PageResult<ProductRepsonse>>> findAll(
                        @RequestParam(required = false, defaultValue = "0") Integer page,
                        @RequestParam(required = false, defaultValue = "5") Integer size,
                        @RequestParam(required = false, defaultValue = "id") String sortField,
                        @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                        HttpServletRequest request) {
                PaginationRequest requestFindAll = new PaginationRequest(page, size, sortField, direction);

                PageResult<ProductRepsonse> repsonse = this.productService.findAll(requestFindAll);
                return ResponseEntity
                                .ok(ResponseUtil.success(repsonse, "findAll Product Successfully",
                                                request.getRequestURI()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductRepsonse>> findById(
                        @PathVariable Integer id,
                        HttpServletRequest request) {

                ProductRepsonse repsonse = this.productService.findByProductId(id);
                return ResponseEntity
                                .ok(ResponseUtil.success(repsonse, "findAll Product Successfully",
                                                request.getRequestURI()));
        }

        // page=0&size=2&sortField=email&direction=DESC
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductRepsonse>> updateProduct(
                        @PathVariable("id") Integer productId,
                        @Valid @RequestBody UpdateProductRequest updateProductRequest,
                        HttpServletRequest request) {
                ProductRepsonse repsonse = this.productService.updateProduct(productId, updateProductRequest);

                return ResponseEntity
                                .ok(ResponseUtil.success(repsonse, "updateProduct Successfully",
                                                request.getRequestURI()));
        }
}
