package com.example.vodconsumer.listener;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.Acknowledgment;

@FunctionalInterface
public interface KafkaConsumer<V extends SpecificRecordBase> {
    public void receive(V payload, String key, Integer partition, Long offset, Acknowledgment ack);
}
