package com.example.inventory.service;

import com.example.inventory.model.InventoryItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InventoryEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishInventoryUpdate(InventoryItem item, String action) {
        try {
            String payload = objectMapper.writeValueAsString(
                    java.util.Map.of("action", action, "item", item)
            );
            kafkaTemplate.send("inventory-updates", payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
