package com.ads.surgery.service;

import com.ads.surgery.entity.Surgery;
import com.ads.surgery.repository.SurgeryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurgeryService {

    @Autowired
    private SurgeryRepository repo;

    public Surgery create(Surgery surgery) {
        surgery.setStatus("PLANNED");
        return repo.save(surgery);
    }

    public List<Surgery> getAll() {
        return repo.findAll();
    }

    public Optional<Surgery> getById(String id) {
        return repo.findById(id);
    }

    public Surgery updateStatus(String id, String newStatus) {
        Surgery s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Surgery not found"));
        s.setStatus(newStatus);
        return repo.save(s);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
