package com.microservices.upload_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.microservices.upload_service.dto.response.ApiResponse;
import com.microservices.upload_service.dto.response.FileResponse;
import com.microservices.upload_service.dto.response.ImageRepsonse;
import com.microservices.upload_service.dto.response.ResponseUtil;
import com.microservices.upload_service.service.UploadService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/file")
    public ResponseEntity<ApiResponse<FileResponse>> uploadFile(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        FileResponse response = this.uploadService.uploadFile(file);
        return ResponseEntity.ok(ResponseUtil.success(response, "File uploaded successfully", null));
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<ApiResponse<FileResponse>> downloadFile(
            @PathVariable String fileName,
            HttpServletRequest request) {
        return null;
        // FileResponse response = this.uploadService.downloadFile(fileName);
        // return ResponseEntity.ok(ResponseUtil.success(response, "File uploaded
        // successfully", null));
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<ImageRepsonse>> uploadImage(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        ImageRepsonse response = this.uploadService.uploadImage(file);
        return ResponseEntity.ok(ResponseUtil.success(response, "Image uploaded successfully", null));
    }
}
