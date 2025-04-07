package com.microservices.upload_service.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.microservices.upload_service.config.ConfigService;
import com.microservices.upload_service.executor.factory.UploadFactory;
import com.microservices.upload_service.executor.product.FileUploader;
import com.microservices.upload_service.executor.product.ImageUploader;

@Component
public class UploadExecutor {
    private final Logger logger = LoggerFactory.getLogger(UploadExecutor.class);

    @Qualifier("localFactory")
    private final UploadFactory localUploadFactory;

    @Qualifier("s3Factory")

    private final UploadFactory s3UploadFactory;

    private final ConfigService configService;

    public UploadExecutor(
            @Qualifier("localFactory") UploadFactory localUploadFactory,
            @Qualifier("s3Factory") UploadFactory s3UploadFactory,
            ConfigService configService) {
        this.localUploadFactory = localUploadFactory;
        this.s3UploadFactory = s3UploadFactory;
        this.configService = configService;
    }

    public String uploadFile(MultipartFile file) {
        logger.info("Upload File Content");
        UploadFactory factory = getUploadFactory();
        FileUploader fileUploader = factory.createFileUploader();
        return fileUploader.uploadFile(file);
    }

    public String uploadImage(MultipartFile file) {
        logger.info("Upload Image Content");
        UploadFactory factory = getUploadFactory();
        ImageUploader imageUploader = factory.createImageUploader();
        return imageUploader.uploadImage(file);
    }

    private UploadFactory getUploadFactory() {
        String uploadDestination = configService.getUploadDestination(); // "LOCAL" or "S3"
        logger.debug("Retrieved upload destination from config: {}", uploadDestination);

        switch (uploadDestination.toUpperCase()) {
            case "LOCAL":
                return localUploadFactory;
            case "S3":
                return s3UploadFactory;
            default:
                logger.warn("Unknown upload destination: {}. Defaulting to LOCAL", uploadDestination);
                return localUploadFactory;
        }
    }
}
