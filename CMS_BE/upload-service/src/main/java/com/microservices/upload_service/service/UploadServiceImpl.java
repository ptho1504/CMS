package com.microservices.upload_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservices.upload_service.executor.UploadExecutor;

import lombok.RequiredArgsConstructor;

@Service
// @GrpcService
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);
    // private final UploadRepository repository;
    private final UploadExecutor executor;

    @Override
    public String test() {
        return "String";
    }

    @Override
    public String uploadFile() {
        try {
            return this.executor.uploadFile();

        } catch (Exception e) {
            logger.error("Some thing wrong in uploadFile", e.getMessage());
            throw e;
        }
    }

    @Override
    public String uploadImage() {
        try {
            return this.executor.uploadImage();

        } catch (Exception e) {
            logger.error("Some thing wrong in uploadImage", e.getMessage());
            throw e;
        }
    }
}
