package com.ads.billing.kafka;

import com.ads.billing.service.BillService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;  // ❌ Commented out
import org.springframework.stereotype.Service;

@Service
public class AppointmentEventListener {

    @Autowired
    private BillService billService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ❌ Commented out for presentation demo (Kafka disabled)
    // @KafkaListener(topics = "appointment.created", groupId = "billing-group")
    public void handleAppointmentCreated(String message) {
        try {
            System.out.println("📨 Billing Service received: appointment.created");
            System.out.println("📨 Raw message: " + message);

            // Parse JSON
            JsonNode appointment = objectMapper.readTree(message);
            String appointmentId = appointment.get("id").asText();
            String patientId = appointment.get("patientId").asText();

            // Auto-create bill (default $150)
            billService.createFromAppointment(appointmentId, patientId, 150.00);

            System.out.println("✅ Bill auto-created for appointment: " + appointmentId);
        } catch (Exception e) {
            System.err.println("❌ Error processing appointment.created: " + e.getMessage());
            e.printStackTrace();
        }
    }
}