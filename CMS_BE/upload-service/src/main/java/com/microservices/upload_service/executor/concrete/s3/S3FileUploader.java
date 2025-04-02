package com.microservices.upload_service.executor.concrete.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microservices.upload_service.executor.product.FileUploader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3FileUploader implements FileUploader {
    private final Logger logger = LoggerFactory.getLogger(S3FileUploader.class);

    @Override
    public String uploadFile(String filePath) {
        logger.info("This is S3 Uploader File");
        return "Uploaded " + filePath + " to S3 bucket";
    }
}
