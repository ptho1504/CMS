package com.microservices.upload_service.service;

import org.springframework.web.multipart.MultipartFile;

import com.microservices.upload_service.dto.response.FileResponse;
import com.microservices.upload_service.dto.response.ImageRepsonse;

public interface UploadService {
    public String test();

    public FileResponse uploadFile(MultipartFile file);

    public ImageRepsonse uploadImage(MultipartFile file);

    public void downloadFile(Object object);
}
