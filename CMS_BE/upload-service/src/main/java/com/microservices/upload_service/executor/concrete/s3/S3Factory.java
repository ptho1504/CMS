package com.microservices.upload_service.executor.concrete.s3;

import org.springframework.stereotype.Component;

import com.microservices.upload_service.executor.factory.UploadFactory;
import com.microservices.upload_service.executor.product.FileUploader;
import com.microservices.upload_service.executor.product.ImageUploader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Factory extends UploadFactory {
    private final S3FileUploader s3FileUploader;
    private final S3ImageUploader s3ImageUploader;

    @Override
    public FileUploader createFileUploader() {
        return s3FileUploader;
    }

    @Override
    public ImageUploader createImageUploader() {
        return s3ImageUploader;
    }

}
