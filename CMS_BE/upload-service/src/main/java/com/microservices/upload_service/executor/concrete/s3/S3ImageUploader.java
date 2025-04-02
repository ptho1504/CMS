package com.microservices.upload_service.executor.concrete.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microservices.upload_service.executor.product.ImageUploader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {
    private final Logger logger = LoggerFactory.getLogger(S3ImageUploader.class);

    @Override
    public String uploadImage(String imagePath) {
        logger.info("This is S3 Uploader Image");
        return "Uploaded image " + imagePath + " to S3 bucket";
    }

}
