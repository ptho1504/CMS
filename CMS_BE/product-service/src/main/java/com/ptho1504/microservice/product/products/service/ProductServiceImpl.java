package com.ptho1504.microservice.product.products.service;

import java.time.LocalDateTime;
import java.util.Date;
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
import com.ptho1504.microservices.product_service.product.DeductStockRequest;
import com.ptho1504.microservices.product_service.product.DeductStockResponse;
import com.ptho1504.microservices.product_service.product.ProductRequest;
import com.ptho1504.microservices.product_service.product.ProductResponse;
import com.ptho1504.microservices.product_service.product.ProductServiceGrpc.ProductServiceImplBase;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@Service
@GrpcService
@RequiredArgsConstructor
public class ProductServiceImpl extends ProductServiceImplBase implements ProductService {

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

    // Grpc - start

    @Override
    public void deductStock(DeductStockRequest request, StreamObserver<DeductStockResponse> responseObserver) {
        logger.info("Received gRPC request for deductStock with productId  " + request.getProductId() + " and quantity "
                + request.getQuantity());
        Integer productId = request.getProductId();
        Integer productQuantity = request.getQuantity();

        Product product = this.repository.findById(productId)
                .orElseThrow(() -> new NotFoundProduct(40001, "Not Found Product"));

        if (product.getStockQuantity() < productQuantity) {
            responseObserver.onNext(DeductStockResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Not enough stock")
                    .build());
            responseObserver.onCompleted();
        }
        else {
            product.setStockQuantity(product.getStockQuantity() - request.getQuantity());
            repository.save(product);
            DeductStockResponse response = DeductStockResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Stock deducted successfully")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getProductStock(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        logger.info("Received gRPC request for getProductStock with Product Id: " + request.getProductId());
        Integer productId = request.getProductId();

        Product product = this.repository.findById(productId)
                .orElseThrow(() -> new NotFoundProduct(40001, "Not Found Product"));

        ProductResponse response = ProductResponse.newBuilder()
                .setId(productId)
                .setDescription(product.getDescription())
                .setName(product.getDescription())
                .setPrice(product.getPrice())
                .setStockQuantity(product.getStockQuantity())
                .setProductType(
                        com.ptho1504.microservices.product_service.product.ProductType
                                .newBuilder()
                                .setId(product.getProductType().getId())
                                .setName(product.getProductType().getName())
                                .build())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Grpc - end
}
