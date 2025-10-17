package com.ads.surgery.entity;

import lombok.*;

// Simple embedded document, not a separate collection

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;
}
