package com.example.websocketdemo.service;

import com.example.websocketdemo.handler.ChatWebSocketHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final ChatWebSocketHandler chatWebSocketHandler;

    public KafkaConsumerService(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @KafkaListener(topics = {"tp_test"}, groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, String> record) {
        LOG.info("kafka message key: {}", record.key());
        LOG.info("kafka message value: {}", record.value());
        chatWebSocketHandler.sendToAllUserMessage("key = "+ record.key() + " value = " + record.value());
    }
}
