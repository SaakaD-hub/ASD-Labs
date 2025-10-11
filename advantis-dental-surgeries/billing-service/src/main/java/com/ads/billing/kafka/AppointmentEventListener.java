package com.ads.billing.kafka;

import com.ads.billing.entity.Bill;
import com.ads.billing.repository.BillRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AppointmentEventListener {

    private final BillRepository billRepository;

    public AppointmentEventListener(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @KafkaListener(topics = "appointment.created", groupId = "billing-group")
    public void handleAppointmentCreated(String message) {
        System.out.println("📨 Received Kafka event: " + message);
        // In future: parse event JSON, create bill automatically
    }
}

