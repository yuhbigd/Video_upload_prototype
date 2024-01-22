package com.example.vodproducer.service;

import com.example.vodproducer.viewmodel.response.UploadFileResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface FileService {
   UploadFileResponse handleSaveFileRequest(HttpServletRequest request);
}
