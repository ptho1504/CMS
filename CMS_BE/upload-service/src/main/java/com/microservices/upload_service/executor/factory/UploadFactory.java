package com.microservices.upload_service.executor.factory;

import com.microservices.upload_service.executor.product.FileUploader;
import com.microservices.upload_service.executor.product.ImageUploader;

public abstract class UploadFactory {

    public abstract FileUploader createFileUploader();

    public abstract ImageUploader createImageUploader();
}
