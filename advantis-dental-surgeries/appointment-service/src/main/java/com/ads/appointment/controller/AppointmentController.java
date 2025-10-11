package com.ads.appointment.controller;

import com.ads.appointment.entity.Appointment;
import com.ads.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment a) {
        return ResponseEntity.ok(service.createAppointment(a));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> all() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> get(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancel(@PathVariable String id) {
        return ResponseEntity.ok(service.cancelAppointment(id));
    }

    @GetMapping("/dentist/{dentistId}")
    public ResponseEntity<List<Appointment>> getByDentist(@PathVariable String dentistId) {
        return ResponseEntity.ok(service.getByDentist(dentistId));
    }
}
