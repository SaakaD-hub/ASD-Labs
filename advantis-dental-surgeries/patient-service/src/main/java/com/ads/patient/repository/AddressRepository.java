package com.ads.patient.repository;

import com.ads.patient.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("""
        SELECT a FROM Address a 
        ORDER BY a.city ASC
    """)
    List<Address> findAllSortedByCity();
}
