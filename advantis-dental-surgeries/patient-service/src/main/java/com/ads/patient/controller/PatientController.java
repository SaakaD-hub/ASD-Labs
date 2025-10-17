package com.ads.patient.controller;

import com.ads.patient.entity.Patient;
import com.ads.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/adsweb/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService service;

    @PostMapping
    public ResponseEntity<Patient> create(@Valid @RequestBody Patient p) {
        Patient patient = service.createPatient(p);
        return ResponseEntity.ok(patient);
    }
//Get All patients  sorted by lastname
     @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(service.findAllSortedByLastName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> get(@PathVariable String id) {
        Patient patient = service.findByIdOrThrow(id);
//                .map(ResponseEntity::ok)
                return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable String id, @RequestBody Patient updated) {
        return ResponseEntity.ok(service.updatePatient(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        String message = service.deletePatient(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Patient>> searchPatients(@PathVariable String searchString) {
        List<Patient> results = service.searchPatients(searchString);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(results);
    }
}
