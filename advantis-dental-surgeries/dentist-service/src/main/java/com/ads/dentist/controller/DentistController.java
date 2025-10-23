package com.ads.dentist.controller;

import com.ads.dentist.entity.Dentist;
import com.ads.dentist.service.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dentists")
public class DentistController {

    @Autowired
    private DentistService service;

    @PostMapping
    public ResponseEntity<Dentist> create(@RequestBody Dentist d) {
        return ResponseEntity.status(201).body(service.createDentist(d));
    }

    @GetMapping
    public ResponseEntity<List<Dentist>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentist> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dentist> update(@PathVariable String id, @RequestBody Dentist newData) {
        return ResponseEntity.ok(service.updateDentist(id, newData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteDentist(id);
        return ResponseEntity.noContent().build();
    }
}
