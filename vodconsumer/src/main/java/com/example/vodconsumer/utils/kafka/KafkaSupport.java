package com.example.vodconsumer.utils.kafka;

import java.util.Optional;

import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class KafkaSupport {
    private KafkaListenerEndpointRegistry registry;

    public void pauseConsume(String containerId, String topic, Integer partition) {
        getContainer(containerId, topic, partition).ifPresent(MessageListenerContainer::pause);
    }

    public void resumeConsumer(String containerId, String topic, Integer partition) {
        getContainer(containerId, topic, partition).ifPresent(MessageListenerContainer::resume);
    }

    public Optional<MessageListenerContainer> getContainer(String containerId, String topic, Integer partition) {
        var containersOpt = Optional
                .ofNullable((ConcurrentMessageListenerContainer<?, ?>) registry.getListenerContainer(containerId));
        if (containersOpt.isPresent()) {
            var containers = containersOpt.get();
            return Optional.of(containers.getContainerFor(topic, partition));
        }
        return Optional.empty();
    }
}
