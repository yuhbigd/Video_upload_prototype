package com.example.vodproducer.service;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

public interface KafkaProducerService<K extends Serializable, V extends SpecificRecordBase> {
    void send(String topicName, K key, V message);
}