package com.etb.testtask.repository;

import com.etb.testtask.model.Bill;

import java.util.List;
import java.util.Optional;

public interface BillRepository {
    Optional<Bill> getById(int id);
    List<Bill> getAll();
    void updateBill(int billId, int customerId, long amount);
    void addBill(int customerId, long amount);
}
