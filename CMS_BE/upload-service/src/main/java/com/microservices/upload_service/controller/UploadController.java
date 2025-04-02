package com.microservices.upload_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.upload_service.service.UploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @GetMapping("/test/file")
    public String testingUploadFile() {
        return this.uploadService.uploadFile();
    }

    @GetMapping("/test/image")
    public String testingUploadImage() {
        return this.uploadService.uploadImage();
    }
}
