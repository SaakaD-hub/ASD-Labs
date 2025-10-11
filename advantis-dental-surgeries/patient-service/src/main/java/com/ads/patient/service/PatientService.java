package com.ads.patient.service;

import com.ads.patient.entity.Patient;
import com.ads.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repo;

    @Autowired
    private KafkaTemplate<String, Object> kafka;

    public Patient createPatient(Patient p) {
        Patient saved = repo.save(p);
        kafka.send("patient.created", saved);
        return saved;
    }

    public List<Patient> findAll() {
        return repo.findAll();
    }

    public Optional<Patient> findById(String id) {
        return repo.findById(id);
    }

    public Patient updatePatient(String id, Patient updated) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setFirstName(updated.getFirstName());
                    existing.setLastName(updated.getLastName());
                    existing.setPhone(updated.getPhone());
                    existing.setEmail(updated.getEmail());
                    existing.setAddress(updated.getAddress());
                    existing.setDob(updated.getDob());
                    return repo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public void deletePatient(String id) {
        repo.deleteById(id);
    }
}

