package com.ads.patient.service;

import com.ads.patient.entity.Address;
import com.ads.patient.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repo;

    public List<Address> getAllAddressesSorted() {
        return repo.findAllSortedByCity();
    }
}
