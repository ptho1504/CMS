package com.microservice.cms.customer_service.customer.handler;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservice.cms.customer_service.customer.dto.response.ApiResponse;
import com.microservice.cms.customer_service.customer.dto.response.ResponseUtil;
import com.microservice.cms.customer_service.customer.exception.CustomerNotFound;
import com.microservice.cms.customer_service.customer.exception.GrpcCustomerNotFound;
import com.ptho1504.microservices.customer_service.customer.ErrorResponseGrpc;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import jakarta.servlet.http.HttpServletRequest;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@RestControllerAdvice
@GrpcAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handle(CustomerNotFound e, HttpServletRequest request) {
        logger.error("{}", e.getMessage());
        return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(), e.getErrorCode(),
                request.getRequestURI());
    }

    @GrpcExceptionHandler(GrpcCustomerNotFound.class)
    public StatusRuntimeException handleGrpcUserExisting(GrpcCustomerNotFound e) {

        Metadata metadata = new Metadata();
        Metadata.Key<ErrorResponseGrpc> errorResponse = ProtoUtils.keyForProto(ErrorResponseGrpc.getDefaultInstance());
        metadata.put(errorResponse, ErrorResponseGrpc.newBuilder().setMessage(e.getMessage())
                .setErrorCode(20002).build());

        var statusRuntimeException = Status.ALREADY_EXISTS.withDescription("Customer not found with userId")
                .asRuntimeException(metadata);
        logger.error("GRPC: {}", metadata.get(errorResponse));
        return statusRuntimeException;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }
}