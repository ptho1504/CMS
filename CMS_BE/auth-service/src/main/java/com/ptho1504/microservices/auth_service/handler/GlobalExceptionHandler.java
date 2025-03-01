package com.ptho1504.microservices.auth_service.handler;

import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ptho1504.microservices.auth_service.dto.response.ApiResponse;
import com.ptho1504.microservices.auth_service.dto.response.ResponseUtil;
import com.ptho1504.microservices.auth_service.exception.PasswordNotMatch;
import com.ptho1504.microservices.auth_service.user.ErrorResponseGrpc;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        logger.error("{}", e.getMessage());
        var errors = new HashMap<String, String>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(PasswordNotMatch.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handle(PasswordNotMatch e, HttpServletRequest request) {
        logger.error("{}", e.getMessage());

        return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(), e.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleGrpcUserExisting(Exception e) {

        Metadata metadata = new Metadata();
        Metadata.Key<ErrorResponseGrpc> errorResponse = ProtoUtils.keyForProto(ErrorResponseGrpc.getDefaultInstance());
        metadata.put(errorResponse, ErrorResponseGrpc.newBuilder().setMessage(e.getMessage())
                .setErrorCode(20002).build());

        var statusRuntimeException = Status.ALREADY_EXISTS.withDescription("An unexpected error occurred. Please try again.")
                .asRuntimeException(metadata);
        logger.error("GRPC: {}", metadata.get(errorResponse));
        return statusRuntimeException;
    }
}