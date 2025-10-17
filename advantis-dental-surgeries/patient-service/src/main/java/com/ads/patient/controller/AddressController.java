package com.ads.patient.controller;

import com.ads.patient.entity.Address;
import com.ads.patient.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adsweb/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = service.getAllAddressesSorted();
        if (addresses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(addresses);
    }
}
