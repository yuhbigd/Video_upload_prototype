package com.example.vodproducer.mapper;

import org.mapstruct.Mapper;

import com.example.vodproducer.entity.FileEntity;
import com.example.vodproducer.viewmodel.response.UploadFileResponse;

@Mapper
public interface UploadFileResponseMapper {
    public UploadFileResponse convertFromFileEntity(FileEntity fileEntity);
}