package com.example.vodconsumer.listener;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.Consumer;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.vodconsumer.service.VideoService;
import com.example.vodconsumer.utils.kafka.KafkaSupport;

import ch.qos.logback.core.util.Duration;
import jakarta.annotation.PostConstruct;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import lombok.RequiredArgsConstructor;
import schema.avro.UploadFileMessage;

@Component
@RequiredArgsConstructor
public class UploadFileMessageListener implements KafkaConsumer<UploadFileMessage> {
    private final VideoService videoService;
    private final KafkaSupport kafkaSupport;
    private final KafkaListenerEndpointRegistry registry;

    @Override
    @KafkaListener(id = "consumer", topics = "file-upload", concurrency = "4")
    public void receive(@Payload UploadFileMessage payload, @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition, @Header(KafkaHeaders.OFFSET) Long offset,
            Acknowledgment ack) {
        // https://github.com/spring-projects/spring-kafka/issues/742#issuecomment-405350961
        kafkaSupport.pauseConsume("consumer", "file-upload", partition);
        CompletableFuture.runAsync(() -> {
            System.out.println("processing " + key + ", " + partition);
            videoService.processVideo(payload);
            ack.acknowledge();
            kafkaSupport.resumeConsumer("consumer", "file-upload", partition);
        }, Executors.newSingleThreadExecutor()).thenAccept((t) -> {
            System.out.println("done " + key + ", " + partition);
        });
    }

    @PostConstruct
    public void log() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable periodicTask = new Runnable() {
            public void run() {
                var consumer = (ConcurrentMessageListenerContainer<?, ?>) registry.getListenerContainer("consumer");
                consumer.getContainers().forEach(c -> {
                    System.out.println(c.getListenerId() + " --- " + c.isPauseRequested());
                });
            }
        };

        executor.scheduleAtFixedRate(periodicTask, 3, 2, TimeUnit.SECONDS);
    }
}
