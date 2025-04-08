package com.microservices.upload_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microservices.upload_service.dto.response.FileResponse;
import com.microservices.upload_service.dto.response.ImageRepsonse;
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
    public FileResponse uploadFile(MultipartFile file) {
        try {
            String urlFile = this.executor.uploadFile(file);
            String filename = file.getName();
            return FileResponse.builder().url(urlFile).filename(filename).build();

        } catch (Exception e) {
            logger.error("Some thing wrong in uploadFile", e.getMessage());
            throw e;
        }
    }

    @Override
    public ImageRepsonse uploadImage(MultipartFile file) {
        try {
            String urlImage = this.executor.uploadImage(file);
            String filename = file.getName();
            return ImageRepsonse.builder().url(urlImage).filename(filename).build();
        } catch (Exception e) {
            logger.error("Some thing wrong in uploadImage", e.getMessage());
            throw e;
        }
    }

    @Override
    public void downloadFile(Object object) {
        try {
            logger.info("Download File");
        } catch (Exception e) {
            logger.error("Some thing wrong in uploadImage", e.getMessage());
            throw e;
        }

    }
}
