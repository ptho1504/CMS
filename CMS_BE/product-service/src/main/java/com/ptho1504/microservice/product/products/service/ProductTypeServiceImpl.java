package com.ptho1504.microservice.product.products.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.product.products.dto.request.CreateProductTypeRequest;
import com.ptho1504.microservice.product.products.dto.request.UpdateProductType;
import com.ptho1504.microservice.product.products.dto.response.ProductTypeResponse;
import com.ptho1504.microservice.product.products.exception.ExistingProductType;
import com.ptho1504.microservice.product.products.exception.NotFoundProductType;
import com.ptho1504.microservice.product.products.mapper.ProductTypeMapper;
import com.ptho1504.microservice.product.products.model.ProductType;
import com.ptho1504.microservice.product.products.repository.ProductTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductTypeRepository repository;
    private final ProductTypeMapper mapper;

    @Override
    public String createProductType(CreateProductTypeRequest createRequest) {
        try {
            Optional<ProductType> prodOptional = this.findByName(createRequest.name());

            if (!prodOptional.isEmpty()) {
                throw new ExistingProductType(40002, "Product Type is existing");
            }

            ProductType productType = mapper.toProductType(createRequest);

            repository.save(productType);

            return String.format("Product Type is created succesfully %s", createRequest.name());
        } catch (Exception e) {
            logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

    @Override
    public String updateProductType(Integer producTypeId, UpdateProductType updateRequest) {
        try {

            Optional<ProductType> prOptionalId = this.findById(producTypeId);

            if (prOptionalId.isEmpty()) {
                throw new NotFoundProductType(40001, "Product Type is not found with id");
            }

            Optional<ProductType> prodOptional = this.findByName(updateRequest.name());

            if (!prodOptional.isEmpty()) {
                throw new ExistingProductType(40002, "Product Type is existing");
            }

            ProductType productType = mapper.toProductType(updateRequest);

            repository.save(productType);

            return String.format("Product Type is updated succesfully %s", updateRequest.name());
        } catch (Exception e) {
            logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<ProductType> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<ProductType> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<ProductTypeResponse> findAll() {
        try {

            List<ProductType> list = this.repository.findAll();

            return list.stream().map(mapper::toProductTypeResponse).toList();

        } catch (Exception e) {
            logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

    @Override
    public String deleteProductTypeById(Integer producTypeId) {
        try {

            Optional<ProductType> prOptionalId = this.findById(producTypeId);

            if (prOptionalId.isEmpty()) {
                throw new NotFoundProductType(40001, "Product Type is not found with id");
            }

            repository.delete(prOptionalId.get());
            return "Delete successfully";
        } catch (Exception e) {
            logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

    @Override
    public ProductTypeResponse findByProductTypeId(Integer id) {
        try {

            Optional<ProductType> prOptionalId = this.findById(id);

            if (prOptionalId.isEmpty()) {
                throw new NotFoundProductType(40001, "Product Type is not found with id");
            }

            return mapper.toProductTypeResponse(prOptionalId.get());
        } catch (Exception e) {
            logger.error("Some thing wrong", e.getMessage());
            throw e;
        }
    }

}
