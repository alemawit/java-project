package com.craft.java.cms.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "customer-topic", groupId = "customer-group")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
        // Add processing logic here (e.g., update database, trigger other actions)
    }
}
