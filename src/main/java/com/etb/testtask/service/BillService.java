package com.etb.testtask.service;

import com.etb.testtask.model.Bill;

import java.util.List;

public interface BillService {
    Bill getBill(int billId);
    List<Bill> getAll();
    void updateBill(int billId, int customerId, long amount);
    void updateBill(Bill bill);
    List<Bill> calcDebit(int creditId, int debitId, long amount);
    List<Bill> calcCredit(int creditId, int debitId, long amount);
    void importBills(List<Bill> importedBills);
}
