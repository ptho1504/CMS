package com.ptho1504.microservice.product.products.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptho1504.microservice.product.products.dto.request.CreateProductTypeRequest;
import com.ptho1504.microservice.product.products.dto.request.UpdateProductType;
import com.ptho1504.microservice.product.products.dto.response.ApiResponse;
import com.ptho1504.microservice.product.products.dto.response.ProductTypeResponse;
import com.ptho1504.microservice.product.products.dto.response.ResponseUtil;
import com.ptho1504.microservice.product.products.service.ProductTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-type")
public class ProductTypeController {
        private final ProductTypeService productTypeService;

        @GetMapping()
        ResponseEntity<ApiResponse<List<ProductTypeResponse>>> findAll(
                        HttpServletRequest request) {

                List<ProductTypeResponse> response = this.productTypeService.findAll();

                return ResponseEntity.ok(
                                ResponseUtil.success(response, "createProductType successfully",
                                                request.getRequestURI()));
        }

        @PostMapping()
        ResponseEntity<ApiResponse<String>> createProductType(
                        @Valid @RequestBody CreateProductTypeRequest createRequest,
                        HttpServletRequest request) {

                String response = this.productTypeService.createProductType(createRequest);

                return ResponseEntity.ok(
                                ResponseUtil.success(response, "createProductType successfully",
                                                request.getRequestURI()));
        }

        @PutMapping("/{id}")
        ResponseEntity<ApiResponse<String>> updateProductType(
                        @PathVariable("id") Integer producTypeId,
                        @Valid @RequestBody UpdateProductType updateRequest,
                        HttpServletRequest request) {

                String response = this.productTypeService.updateProductType(producTypeId, updateRequest);

                return ResponseEntity.ok(
                                ResponseUtil.success(response, "createProductType successfully",
                                                request.getRequestURI()));
        }

        @DeleteMapping("/{id}")
        ResponseEntity<ApiResponse<String>> updateProductType(
                        @PathVariable("id") Integer producTypeId,
                        HttpServletRequest request) {

                String response = this.productTypeService.deleteProductTypeById(producTypeId);

                return ResponseEntity.ok(
                                ResponseUtil.success(response, "createProductType successfully",
                                                request.getRequestURI()));
        }
}
