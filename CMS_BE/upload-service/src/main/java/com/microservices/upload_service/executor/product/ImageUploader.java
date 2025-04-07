package com.microservices.upload_service.executor.product;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    public String uploadImage(MultipartFile file);
}
