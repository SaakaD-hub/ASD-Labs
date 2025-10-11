package com.ads.billing.service;

import com.ads.billing.entity.Bill;
import com.ads.billing.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository repo;

    public Bill create(Bill bill) {
        bill.setStatus("UNPAID");
        return repo.save(bill);
    }

    public List<Bill> getAll() {
        return repo.findAll();
    }

    public Optional<Bill> getById(String id) {
        return repo.findById(id);
    }

    public Bill payBill(String id) {
        Bill bill = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setStatus("PAID");
        return repo.save(bill);
    }
}

