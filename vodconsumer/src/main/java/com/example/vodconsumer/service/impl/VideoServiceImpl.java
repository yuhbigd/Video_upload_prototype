package com.example.vodconsumer.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.vodconsumer.entity.FileEntity;
import com.example.vodconsumer.repository.FileRepository;
import com.example.vodconsumer.service.VideoService;
import com.example.vodconsumer.utils.constant.FileStatus;

import schema.avro.UploadFileMessage;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedDirectoryUpload;
import software.amazon.awssdk.transfer.s3.model.DirectoryUpload;
import software.amazon.awssdk.transfer.s3.model.UploadDirectoryRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    @Value("${application.s3.url}")
    private String s3Url;
    @Value("${application.s3.bucket}")
    private String bucket;

    private final S3TransferManager transferManager;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public void processVideo(UploadFileMessage message) {

        String INPUT_FILE = s3Url + "/" + message.getUrl();
        String directoryName = "ffmpeg/" + message.getId() + "_" + UUID.randomUUID();
        String FFMPEG_PATH = directoryName + "/output.mpd";

        ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", INPUT_FILE, "-map", "0", "-map", "0", "-map",
                "0", "-c:v:0", "libx264", "-b:v:0", "2000k", "-s:v:0", "1920x1080", "-c:v:1", "libx264", "-b:v:1",
                "1000k", "-s:v:1", "1280x720", "-c:v:2", "libx264", "-b:v:2", "500k", "-s:v:2", "640x360", "-c:a",
                "aac", "-b:a", "128k", "-use_template", "1", "-use_timeline", "1", "-seg_duration", "4",
                "-hls_playlist", "true", "-f", "dash", FFMPEG_PATH);
        try {
            Files.createDirectories(Paths.get(directoryName));
            Process process = processBuilder.start();
            new Thread(() -> {
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                }
            }).start();
            new Thread(() -> {
                try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()))) {
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                }
            }).start();
            if (process.waitFor() != 0) {
                throw new RuntimeException("ERROR WHEN PROCESS VIDEO");
            }
            String s3FolderPrefix =  "/" + message.getId(); // EEERRRR
            uploadDirectory(directoryName, bucket, s3FolderPrefix);
            FileEntity file = fileRepository.getReferenceById(message.getId());
            file.setStatus(FileStatus.DONE);
            file.setDashUrl(s3FolderPrefix + "/" + "output.mpd");
            file.setHlsUrl(s3FolderPrefix + "/" + "master.m3u8");
            fileRepository.save(file);
        } catch (Exception e) {
            Path directoryPath = Path.of(directoryName);
            if (Files.exists(directoryPath)) {
                try {
                    FileUtils.deleteDirectory(directoryPath.toFile());
                } catch (IOException e1) {
                    throw new RuntimeException("LEAKED RESOURCE");
                }
            }
            e.printStackTrace();
        } finally {

        }
    }

    public Integer uploadDirectory(String sourceDirectory, String bucketName, String prefix) throws IOException {
        DirectoryUpload directoryUpload = transferManager.uploadDirectory(UploadDirectoryRequest.builder()
                .source(Paths.get(sourceDirectory))
                .s3Prefix(prefix)
                .bucket(bucketName)
                .build());
        CompletedDirectoryUpload completedDirectoryUpload = directoryUpload.completionFuture().join();
        completedDirectoryUpload.failedTransfers()
                .forEach(fail -> System.out.println("Object [{}] failed to transfer " + fail.toString()));
        FileUtils.deleteDirectory(new File(sourceDirectory));
        return completedDirectoryUpload.failedTransfers().size();
    }
}
