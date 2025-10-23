package com.ads.billing.controller;

import com.ads.billing.entity.Bill;
import com.ads.billing.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")  // ✅ Correct mapping
public class BillController {

    @Autowired
    private BillService service;

    @PostMapping
    public ResponseEntity<Bill> create(@RequestBody Bill bill) {
        return ResponseEntity.status(201).body(service.create(bill));
    }

    @GetMapping
    public ResponseEntity<List<Bill>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> get(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get bills by patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Bill>> getByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(service.getByPatient(patientId));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Bill> pay(@PathVariable String id) {
        return ResponseEntity.ok(service.payBill(id));
    }
}