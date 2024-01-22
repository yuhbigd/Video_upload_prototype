package com.example.vodproducer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.vodproducer.entity.FileEntity;

import schema.avro.UploadFileMessage;

@Mapper(componentModel = "spring")
public interface UploadFileMessageMapper {
    @Mapping(target = "url", constant = "")
    @Mapping(target = "timestamp", expression = "java(java.time.Instant.now())")
    public UploadFileMessage convertFromFileEntity(FileEntity file);
}