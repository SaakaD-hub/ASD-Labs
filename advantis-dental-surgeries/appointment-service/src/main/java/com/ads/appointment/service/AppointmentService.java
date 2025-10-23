package com.ads.appointment.service;

import com.ads.appointment.entity.Appointment;
import com.ads.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repo;

    @Autowired
    private KafkaTemplate<String, Object> kafka;

    public Appointment createAppointment(Appointment a) {
        a.setStatus("BOOKED");
        Appointment saved = repo.save(a);
        kafka.send("appointment.created", saved);
        return saved;
    }

    public List<Appointment> getAll() {
        return repo.findAll();
    }

    public Optional<Appointment> getById(String id) {
        return repo.findById(id);
    }

    // ✅ NEW: Get by patient
    public List<Appointment> getByPatient(String patientId) {
        return repo.findByPatientId(patientId);
    }

    public List<Appointment> getByDentist(String dentistId) {
        return repo.findByDentistId(dentistId);
    }

    public Appointment cancelAppointment(String id) {
        Appointment a = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        a.setStatus("CANCELED");
        repo.save(a);
        kafka.send("appointment.canceled", a);
        return a;
    }
}