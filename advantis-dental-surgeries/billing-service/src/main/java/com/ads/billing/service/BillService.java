package com.ads.billing.service;

import com.ads.billing.entity.Bill;
import com.ads.billing.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.core.KafkaTemplate;  // ❌ Remove Kafka import
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository repo;

    // ❌ Remove Kafka dependency completely
    // @Autowired(required = false)
    // private KafkaTemplate<String, Object> kafka;

    public Bill create(Bill bill) {
        bill.setStatus("UNPAID");
        bill.setCreatedAt(LocalDateTime.now());
        Bill saved = repo.save(bill);

        // ❌ Remove Kafka publish
        // if (kafka != null) {
        //     kafka.send("bill.created", saved);
        // }

        return saved;
    }

    public Bill createFromAppointment(String appointmentId, String patientId, double amount) {
        Bill bill = new Bill();
        bill.setAppointmentId(appointmentId);
        bill.setPatientId(patientId);
        bill.setAmount(amount);
        bill.setStatus("UNPAID");
        bill.setCreatedAt(LocalDateTime.now());

        Bill saved = repo.save(bill);

        // ❌ Remove Kafka publish
        // if (kafka != null) {
        //     kafka.send("bill.created", saved);
        // }

        System.out.println("✅ Bill created for appointment: " + appointmentId);
        return saved;
    }

    public List<Bill> getAll() {
        return repo.findAll();
    }

    public Optional<Bill> getById(String id) {
        return repo.findById(id);
    }

    public List<Bill> getByPatient(String patientId) {
        return repo.findByPatientId(patientId);
    }

    public Bill payBill(String id) {
        Bill bill = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setStatus("PAID");
        Bill saved = repo.save(bill);

        // ❌ Remove Kafka publish (THIS WAS CAUSING THE 500 ERROR!)
        // if (kafka != null) {
        //     kafka.send("bill.paid", saved);
        // }

        System.out.println("✅ Bill paid: " + id);
        return saved;
    }
}