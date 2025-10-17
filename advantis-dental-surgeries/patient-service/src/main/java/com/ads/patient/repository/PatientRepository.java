package com.ads.patient.repository;

import com.ads.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    @Query("""
        SELECT p FROM Patient p 
        JOIN FETCH p.address a 
        WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(p.email) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(p.phone) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(a.city) LIKE LOWER(CONCAT('%', :search, '%'))
    """)
    List<Patient> searchPatients(@Param("search") String search);
}


