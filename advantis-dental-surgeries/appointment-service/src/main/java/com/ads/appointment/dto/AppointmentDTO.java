package com.ads.appointment.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long id;
    private Long patientId;
    private Long dentistId;
    private Long surgeryId;
    private LocalDateTime appointmentDate;
}
