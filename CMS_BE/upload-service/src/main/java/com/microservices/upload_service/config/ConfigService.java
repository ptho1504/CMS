package com.microservices.upload_service.config;

import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    public String getUploadDestination() {
        return "S3"; // Hardcoded for simplicity; replace with DB logic
    }
}
