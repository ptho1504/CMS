package com.microservices.upload_service.executor.concrete.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microservices.upload_service.executor.product.FileUploader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalFileUploader implements FileUploader {
    private final Logger logger = LoggerFactory.getLogger(LocalFileUploader.class);

    @Override
    public String uploadFile(String filePath) {
        logger.info("This is Local Uploader File");
        return "Uploaded File " + filePath + " to local storage";
    }
}
