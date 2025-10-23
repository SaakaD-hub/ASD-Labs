package com.ads.billing.dto;

import lombok.Data;

@Data
public class BillDTO {
    private Long id;
    private Long appointmentId;
    private Double amount;
    private String status; // PAID, UNPAID
}
