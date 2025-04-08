package com.microservices.upload_service.executor.concrete.s3;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.microservices.upload_service.exception.InvalidFileTypeException;
import com.microservices.upload_service.exception.NotFoundFileException;
import com.microservices.upload_service.executor.product.FileUploader;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3FileUploader implements FileUploader {
    private final Logger logger = LoggerFactory.getLogger(S3FileUploader.class);
    private final S3Client s3Client;
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx
            "application/zip",
            "text/plain");
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            logger.info("This is S3 Uploader File");
            if (file.isEmpty()) {
                throw new NotFoundFileException(70001, "Failed to upload empty file");
            }

            // Valiate File Content
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
                throw new InvalidFileTypeException(70002, "Unsupported file type: " + contentType);
            }
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .contentType(file.getContentType())
                    // .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return "https://" + bucketName + ".s3.amazonaws.com/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
