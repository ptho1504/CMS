package com.microservices.upload_service.executor.concrete.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microservices.upload_service.executor.product.ImageUploader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalImageUploader implements ImageUploader {
    private final Logger logger = LoggerFactory.getLogger(LocalImageUploader.class);

    @Override
    public String uploadImage(String imagePath) {
        logger.info("This is Local Uploader Image");
        return "Uploaded image " + imagePath + " to local storage";
    }

}
