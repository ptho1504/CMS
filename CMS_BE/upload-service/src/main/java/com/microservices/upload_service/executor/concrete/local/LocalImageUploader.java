package com.microservices.upload_service.executor.concrete.local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.microservices.upload_service.exception.InvalidFileTypeException;
import com.microservices.upload_service.exception.NotFoundFileException;
import com.microservices.upload_service.executor.product.ImageUploader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LocalImageUploader implements ImageUploader {
    private final Logger logger = LoggerFactory.getLogger(LocalImageUploader.class);
    private static final String STORAGE_DIRECTORY = "/uploads/images";
    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/jpg", "image/webp");
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            logger.info("This is Local Uploader Image");

            if (file.isEmpty()) {
                throw new NotFoundFileException(70001, "Failed to upload empty file");
            }

            // Valiate File Content
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
                throw new InvalidFileTypeException(70002, "Unsupported file type: " + contentType);
            }

            if (file.getSize() > MAX_SIZE) {
                throw new IllegalArgumentException("File exceeds max size (5MB)");
            }

            // Create directory
            String projectRoot = System.getProperty("user.dir");
            Path uploadPath = Paths.get(projectRoot, "upload-service", STORAGE_DIRECTORY);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/upload/images/" + fileName;

        } catch (IOException e) {
            logger.error("IO error during file upload", e);
            throw new RuntimeException("File upload failed", e);

        } catch (Exception e) {
            logger.error("Some thing wrong in uploadFile Local", e);
            throw e;
        }
    }

}
