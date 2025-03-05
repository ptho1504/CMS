package com.ptho1504.microservice.product.products.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptho1504.microservice.product.products.dto.request.CreateProductRequest;
import com.ptho1504.microservice.product.products.dto.request.PaginationRequest;
import com.ptho1504.microservice.product.products.dto.request.UpdateProductRequest;
import com.ptho1504.microservice.product.products.dto.response.PageResult;
import com.ptho1504.microservice.product.products.dto.response.ProductRepsonse;
import com.ptho1504.microservice.product.products.exception.NotFoundProduct;
import com.ptho1504.microservice.product.products.exception.NotFoundProductType;
import com.ptho1504.microservice.product.products.mapper.ProductMapper;
import com.ptho1504.microservice.product.products.model.Product;
import com.ptho1504.microservice.product.products.model.ProductType;
import com.ptho1504.microservice.product.products.repository.ProductRepository;
import com.ptho1504.microservice.product.products.util.PaginationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductTypeService productTypeService;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductMapper mapper;

    @Override
    public ProductRepsonse createProduct(CreateProductRequest createRequest) {
        try {
            Optional<ProductType> optional = this.productTypeService.findById(createRequest.productTypeId());

            if (optional.isEmpty()) {
                throw new NotFoundProductType(40001, "Product Type not found");
            }

            ProductType productType = optional.get();

            Product product = mapper.toProduct(createRequest);

            product.setProductType(productType);

            Product saveProduct = repository.save(product);

            return mapper.toProductRepsonse(saveProduct);

        } catch (Exception e) {
            logger.error("Some thing error", e.getMessage());
            throw e;
        }
    }

    @Override
    public PageResult<ProductRepsonse> findAll(PaginationRequest requestFindAll) {
        try {
            Pageable pageable = PaginationUtils.getPageable(requestFindAll.getPage(), requestFindAll.getSize(),
                    requestFindAll.getDirection(),
                    requestFindAll.getSortField());
            Page<Product> entities = repository.findAll(pageable);

            List<ProductRepsonse> entitiesDto = entities.stream().map(mapper::toProductRepsonse).toList();

            return new PageResult<ProductRepsonse>(
                    entitiesDto,
                    entities.getTotalPages(),
                    entities.getTotalElements(),
                    entities.getSize(),
                    entities.getNumber(),
                    entities.isEmpty());
        } catch (Exception e) {
            logger.error("Some thing error", e.getMessage());
            throw e;
        }
    }

    @Override
    public ProductRepsonse findByProductId(Integer id) {
        try {
            Optional<Product> optional = this.findById(id);

            if (optional.isEmpty()) {
                throw new NotFoundProduct(40002, "Product not found");
            }

            return mapper.toProductRepsonse(optional.get());

        } catch (Exception e) {
            logger.error("Some thing error", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public ProductRepsonse updateProduct(Integer productId, UpdateProductRequest updateProductRequest) {
        try {
            Optional<Product> optional = this.findById(productId);

            if (optional.isEmpty()) {
                throw new NotFoundProduct(40002, "Product not found");
            }

            Optional<ProductType> optionalProducType = this.productTypeService
                    .findById(updateProductRequest.productTypeId());

            if (optionalProducType.isEmpty()) {
                throw new NotFoundProductType(40001, "Product Type not found");
            }

            Product product = optional.get();

            product.setName(updateProductRequest.name());
            product.setDescription(updateProductRequest.description());
            product.setPrice(updateProductRequest.price());
            product.setStockQuantity(updateProductRequest.stockQuantity());
            product.setUpdatedAt(LocalDateTime.now());
            product.setProductType(optionalProducType.get());

            Product savedProduct = this.repository.save(product);

            return mapper.toProductRepsonse(savedProduct);

        } catch (Exception e) {
            logger.error("Some thing error", e.getMessage());
            throw e;
        }
    }

}
