package com.microservices.upload_service.handler;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.devh.boot.grpc.server.advice.GrpcAdvice;

@RestControllerAdvice
@GrpcAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler(UserNotFoundException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public ApiResponse<Object> handle(UserNotFoundException e, HttpServletRequest
    // request) {
    // logger.error("{}", e.getMessage());
    // return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(),
    // e.getErrorCode(),
    // request.getRequestURI());
    // }

    // @ExceptionHandler(UserExistingException.class)
    // @ResponseStatus(HttpStatus.CONFLICT)
    // public ApiResponse<Object> handle(UserExistingException e, HttpServletRequest
    // request) {
    // logger.error("{}", e.getMessage());
    // return ResponseUtil.error(Arrays.asList(e.getMessage()), e.getMessage(),
    // e.getErrorCode(),
    // request.getRequestURI());
    // }

    // @GrpcExceptionHandler(UserExistingExceptionGrpc.class)
    // public StatusRuntimeException
    // handleGrpcUserExisting(UserExistingExceptionGrpc e) {

    // Metadata metadata = new Metadata();
    // Metadata.Key<ErrorResponseGrpc> errorResponse =
    // ProtoUtils.keyForProto(ErrorResponseGrpc.getDefaultInstance());
    // metadata.put(errorResponse,
    // ErrorResponseGrpc.newBuilder().setMessage(e.getMessage())
    // .setErrorCode(20002).build());

    // var statusRuntimeException = Status.ALREADY_EXISTS.withDescription("User
    // existing found with email")
    // .asRuntimeException(metadata);
    // logger.error("GRPC: {}", metadata.get(errorResponse));
    // return statusRuntimeException;
    // }

    @ExceptionHandler(MethodArgumentNotValidException.class)
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again.");
    }
}