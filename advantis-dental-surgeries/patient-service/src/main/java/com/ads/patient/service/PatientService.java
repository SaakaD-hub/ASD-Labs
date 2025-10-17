package com.ads.patient.service;

import com.ads.patient.entity.Patient;
import com.ads.patient.exception.PatientNotFoundException;
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

    public List<Patient> findAllSortedByLastName() {
        return repo.findAll().stream()
                .sorted((p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()))
                .toList();
    }

    public Patient findByIdOrThrow(String id) {
        return repo.findById(id)
                .orElseThrow(()-> new PatientNotFoundException(id));
    }

    public Patient updatePatient(String id, Patient updated) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setFirstName(updated.getFirstName());
                    existing.setLastName(updated.getLastName());
                    existing.setGender(updated.getGender());
                    existing.setPhone(updated.getPhone());
                    existing.setEmail(updated.getEmail());
                    existing.setAddress(updated.getAddress());
                    existing.setDob(updated.getDob());
                    if (existing.getAddress() != null && updated.getAddress() != null) {
                        //Keep the same address entity
                        existing.getAddress().setStreet(updated.getAddress().getStreet());
                        existing.getAddress().setCity(updated.getAddress().getCity());
                        existing.getAddress().setState(updated.getAddress().getState());
                        existing.getAddress().setZip(updated.getAddress().getZip());
                    } else if(updated.getAddress() != null) {
                        // If patient had no address before, set the new one
                        existing.setAddress(updated.getAddress());
                    }
                    return repo.save(existing);
                })
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
    }

    public String deletePatient(String id) {
        Patient patient = repo.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(" Patient with ID" + id + " not found"));
        repo.delete(patient);
        kafka.send("patient.deleted", id);
        return "Patient with ID " + id + " has been deleted.";
    }
    public List<Patient> searchPatients(String searchString) {
        return repo.searchPatients(searchString);
    }

}

