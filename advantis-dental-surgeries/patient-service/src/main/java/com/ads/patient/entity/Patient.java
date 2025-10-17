package com.ads.patient.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.ads.patient.entity.Address;



@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "patient_id")
    private String id;
    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;
    @NotBlank(message = "Gender should be specified")
    private String gender;
    //@NotEmpty(message = "Phone number cannot be empty")
    @Column(nullable = false)
    private String phone;
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @NotNull(message = "Address cannot be empty")
    private Address address;
    @NotNull(message = "Date of Birth is required")
    private LocalDate dob;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Address getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
}

