package edu.miu.cs.cs489.lab2b.pams.model;

import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private String address;
    private LocalDate dob;

    public Patient(int id, String firstName, String lastName,
                   String phoneNo, String email, String address, LocalDate dob) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.address = address;
        this.dob = dob;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNo() { return phoneNo; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public LocalDate getDob() { return dob; }

    public int getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
