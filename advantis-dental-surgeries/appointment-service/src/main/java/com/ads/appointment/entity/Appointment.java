package com.ads.appointment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "appointment_id", nullable = false, updatable = false)
    private String id;

    @Column(name = "patient_id")
    private String patientId;
    @Column(name = "dentist_id")
    private String dentistId;
    @Column(name = "appointment_date")
    private LocalDate date;
    @Column(name = "appointment_time")
    private LocalTime time;
    private String status; // BOOKED | CANCELED | COMPLETED

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDentistId() {
        return dentistId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDentistId(String dentistId) {
        this.dentistId = dentistId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }





// Getters and setters
}
