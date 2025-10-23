package com.ads.dentist.service;

import com.ads.dentist.entity.Dentist;
import com.ads.dentist.repository.DentistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistService {

    @Autowired
    private DentistRepository repo;

    @Autowired
    private KafkaTemplate<String, Object> kafka;

    public Dentist createDentist(Dentist d) {
        Dentist saved = repo.save(d);
       // kafka.send("dentist.created", saved);
        // kafka.send("appointment.limit.check", saved.getId()); // Notify appointment service
        System.out.println("✅ Dentist created: " + saved.getId());
        return saved;
    }

    public List<Dentist> findAll() {
        return repo.findAll();
    }

    public Optional<Dentist> findById(String id) {
        return repo.findById(id);
    }

    public Dentist updateDentist(String id, Dentist newData) {
        return repo.findById(id).map(existing -> {
            existing.setFirstName(newData.getFirstName());
            existing.setLastName(newData.getLastName());
            existing.setPhone(newData.getPhone());
            existing.setEmail(newData.getEmail());
            existing.setSpecialization(newData.getSpecialization());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Dentist not found"));
    }

    public void deleteDentist(String id) {
        repo.deleteById(id);
    }
}
