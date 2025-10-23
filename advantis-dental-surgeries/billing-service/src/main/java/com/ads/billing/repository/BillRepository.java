package com.ads.billing.repository;

import com.ads.billing.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {
    List<Bill> findByPatientId(String patientId);  // ✅ Find by patient
}