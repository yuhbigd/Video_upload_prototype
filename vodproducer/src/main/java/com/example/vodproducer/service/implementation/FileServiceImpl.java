package com.example.vodproducer.service.implementation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.fileupload2.core.FileItemInput;
import org.apache.commons.fileupload2.core.ProgressListener;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vodproducer.entity.FileEntity;
import com.example.vodproducer.exception.BadRequestException;
import com.example.vodproducer.exception.InternalServerException;
import com.example.vodproducer.mapper.UploadFileMessageMapper;
import com.example.vodproducer.mapper.UploadFileResponseMapper;
import com.example.vodproducer.repository.FileRepository;
import com.example.vodproducer.service.FileService;
import com.example.vodproducer.service.KafkaProducerService;
import com.example.vodproducer.utils.constant.Constants;
import com.example.vodproducer.utils.constant.FileStatus;
import com.example.vodproducer.viewmodel.response.UploadFileResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import schema.avro.UploadFileMessage;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.BlockingInputStreamAsyncRequestBody;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.Upload;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final S3TransferManager transferManager;
    private final KafkaProducerService<String, UploadFileMessage> kafkaProducer;
    private final UploadFileMessageMapper uploadFileMessageMapper;
    private final UploadFileResponseMapper uploadFileResponseMapper;

    @Value("${application.s3.bucket}")
    private String bucket;
    @Value("${application.kafka.upload-file-topic}")
    private String kafkaTopic;

    @Override
    @Transactional
    public UploadFileResponse handleSaveFileRequest(HttpServletRequest request) {
        boolean isMultipart = JakartaServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            throw new BadRequestException(Constants.ERROR_CODE.FILE_IS_NOT_FOUND);
        }
        JakartaServletFileUpload jakartaServletFileUpload = new JakartaServletFileUpload();
        jakartaServletFileUpload.setProgressListener(progressListener());
        try {
            List<FileEntity> fileEntities = new ArrayList<>();
            jakartaServletFileUpload.getItemIterator(request).forEachRemaining(item -> {
                try (InputStream stream = item.getInputStream();) {
                    checkItem(item);
                    String name = UUID.randomUUID().toString() + "_" + item.getName();
                    log.info("File field " + item.getFieldName() + " with file name " + name + " detected.");

                    BlockingInputStreamAsyncRequestBody body = AsyncRequestBody.forBlockingInputStream(null);
                    Upload upload = transferManager.upload(builder -> builder
                            .requestBody(body)
                            .putObjectRequest(req -> req.bucket(bucket).key("original" + "/" + name))
                            .build());
                    body.writeInputStream(stream);
                    FileEntity fileEntity = FileEntity.builder().name(name).status(FileStatus.PROCESSING)
                            .build();
                    fileRepository.save(fileEntity);
                    UploadFileMessage uploadFileMessage = uploadFileMessageMapper.convertFromFileEntity(fileEntity);
                    uploadFileMessage.setUrl(bucket + "/original" + "/" + name);
                    kafkaProducer.send(kafkaTopic, Long.toString(uploadFileMessage.getId()), uploadFileMessage);
                    upload.completionFuture().join();
                    fileEntities.add(fileEntity);
                }
            });
            return uploadFileResponseMapper.convertFromFileEntity(fileEntities.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerException(Constants.ERROR_CODE.INTERNAL_SERVER_ERROR);
        }
    }

    private void checkItem(FileItemInput item) {
        if (item.isFormField() || !item.getFieldName().equals("file")) {
            throw new BadRequestException(Constants.ERROR_CODE.FILE_IS_NOT_FOUND);
        }
        String[] videoMimeTypes = Constants.MIME_TYPE.VIDEO_TYPE;
        for (String videoMimeType : videoMimeTypes) {
            if (videoMimeType.equals(item.getContentType())) {
                return;
            }
        }
        throw new BadRequestException(Constants.ERROR_CODE.FILE_TYPE_NOT_VALID);
    }

    private ProgressListener progressListener() {
        AtomicInteger percent = new AtomicInteger(0);
        return (bytesRead, contentLength, items) -> {
            int newPercent = (int) (100 * (double) bytesRead / contentLength);
            if (newPercent >= percent.get() + 5) {
                percent.set(newPercent / 5 * 5);
                System.out.println("Progress: " + percent + "%");
            }
        };
    }
}