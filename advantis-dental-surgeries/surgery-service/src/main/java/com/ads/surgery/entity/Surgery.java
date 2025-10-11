package com.ads.surgery.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "surgeries")
public class Surgery {

    @Id
    private String id;
    private String patientId;
    private String dentistId;
    private String procedureType;
    private LocalDateTime scheduledAt;
    private String status;           // PLANNED | IN_PROGRESS | COMPLETED | CANCELED
    private List<String> notes;      // Dynamic document content

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getDentistId() { return dentistId; }
    public void setDentistId(String dentistId) { this.dentistId = dentistId; }

    public String getProcedureType() { return procedureType; }
    public void setProcedureType(String procedureType) { this.procedureType = procedureType; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getNotes() { return notes; }
    public void setNotes(List<String> notes) { this.notes = notes; }
}

