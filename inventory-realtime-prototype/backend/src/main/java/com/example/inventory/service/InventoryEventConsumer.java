package com.example.inventory.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class InventoryEventConsumer {
    private final SimpMessagingTemplate template;

    public InventoryEventConsumer(SimpMessagingTemplate template) {
        this.template = template;
    }

    @KafkaListener(topics = "inventory-updates", groupId = "inventory-group")
    public void listen(String message) {
        // forward Kafka message to connected websocket clients
        this.template.convertAndSend("/topic/inventory", message);
    }
}
