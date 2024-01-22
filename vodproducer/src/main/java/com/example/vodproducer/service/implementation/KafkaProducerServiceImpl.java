package com.example.vodproducer.service.implementation;

import java.io.Serializable;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.vodproducer.service.KafkaProducerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducerServiceImpl<K extends Serializable, V extends SpecificRecordBase>
        implements KafkaProducerService<K, V> {
    private final KafkaTemplate<K, V> kafkaTemplate;

    @Override
    public void send(String topicName, K key, V message) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            this.kafkaTemplate.send(topicName, key, message);
            log.info("Sent message={} to topic={}", message, topicName);
        } catch (Exception e) {
            log.error("ERROR WHEN SEND MESSAGE TO KAFKA, DETAIL: ", e);
        }
    }

}