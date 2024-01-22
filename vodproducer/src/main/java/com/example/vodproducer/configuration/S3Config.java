package com.example.vodproducer.configuration;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
public class S3Config {
    @Bean
    S3Client buildS3Client() {
        Region region = Region.US_EAST_1;
        S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create("localstack"))
                .endpointOverride(URI.create("https://s3.localhost.localstack.cloud:4566"))
                .build();
        return s3Client;
    }

    @Bean
    S3TransferManager buildS3TransferManager() {
        S3AsyncClient s3AsyncClient = S3AsyncClient.crtBuilder()
                .credentialsProvider(ProfileCredentialsProvider.create("localstack"))
                .endpointOverride(URI.create("https://s3.localhost.localstack.cloud:4566"))
                .region(Region.US_WEST_2)
                .targetThroughputInGbps(20.0)
                .minimumPartSizeInBytes(8 * 1025 * 1024L)
                .build();
        S3TransferManager transferManager = S3TransferManager.builder()
                .s3Client(s3AsyncClient)
                .build();
        return transferManager;

    }
}
