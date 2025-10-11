package com.ads.appointment.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DentistEventConsumer {

    @KafkaListener(topics = "appointment.limit.check", groupId = "appointment-group")
    public void handleLimitCheck(String dentistId) {
        System.out.println("Received appointment.limit.check for dentist ID: " + dentistId);
        // Future logic: restrict more than 5 appointments per week
    }
}
