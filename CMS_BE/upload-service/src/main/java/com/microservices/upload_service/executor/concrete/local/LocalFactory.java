package com.microservices.upload_service.executor.concrete.local;

import org.springframework.stereotype.Component;

import com.microservices.upload_service.executor.factory.UploadFactory;
import com.microservices.upload_service.executor.product.FileUploader;
import com.microservices.upload_service.executor.product.ImageUploader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalFactory extends UploadFactory {
    private final LocalFileUploader localFileUploader;
    private final LocalImageUploader localImageUploader;

    @Override
    public FileUploader createFileUploader() {
        return localFileUploader;
    }

    @Override
    public ImageUploader createImageUploader() {
        return localImageUploader;
    }

}
