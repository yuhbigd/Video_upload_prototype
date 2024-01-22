package com.example.vodproducer.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.vodproducer.service.FileService;
import com.example.vodproducer.viewmodel.response.UploadFileResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadFileResponse> postMethodName(HttpServletRequest request) {
        return ResponseEntity.ok(fileService.handleSaveFileRequest(request));
    }

}