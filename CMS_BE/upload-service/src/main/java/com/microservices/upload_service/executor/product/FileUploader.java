package com.microservices.upload_service.executor.product;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    public String uploadFile(MultipartFile file);;
}
