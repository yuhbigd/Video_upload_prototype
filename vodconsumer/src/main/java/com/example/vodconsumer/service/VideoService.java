package com.example.vodconsumer.service;

import schema.avro.UploadFileMessage;

public interface VideoService {
    public void processVideo(UploadFileMessage message);
}