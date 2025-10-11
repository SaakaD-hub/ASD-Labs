package com.ads.surgery.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SurgeryEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public SurgeryEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(String message) {
        kafkaTemplate.send("surgery.events", message);
        System.out.println("📨 Published Surgery Event → " + message);
    }
}

