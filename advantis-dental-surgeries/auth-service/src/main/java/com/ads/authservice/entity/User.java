package com.ads.authservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true, nullable = false)  // ✅ NEW
    private String email;

    @Column(nullable = false)
    private String role;  // ROLE_MANAGER, ROLE_DENTIST, ROLE_PATIENT

    // ✅ NEW - Additional fields
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean enabled = true;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}