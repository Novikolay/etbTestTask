package com.etb.testtask.service;

import com.etb.testtask.model.ActionType;
import com.etb.testtask.model.Bill;
import com.etb.testtask.repository.ActionRepository;
import com.etb.testtask.repository.BillRepository;
import com.etb.testtask.utils.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Log
@Primary
@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final ActionRepository actionRepository;

    @Override
    public Bill getBill(int billId) {
        return billRepository.getById(billId)
                .orElseThrow(() -> new EntityNotFoundException(billId));
    }

    @Override
    public List<Bill> getAll() {
        return billRepository.getAll();
    }

    @Override
    public void updateBill(int billId, int customerId, long amount) {
        billRepository.updateBill(billId, customerId, amount);
    }

    @Override
    public void updateBill(Bill bill) {
        billRepository.updateBill(bill.getId(), bill.getCustomer().getId(), bill.getAmount());
    }

    @Override
    public List<Bill> calcDebit(int creditId, int debitId, long amount) {
        Bill credit = getBill(creditId);
        Bill debit = getBill(debitId);

        credit.setAmount(credit.getAmount() - amount);
        debit.setAmount(debit.getAmount() + amount);

        logActions(creditId, debitId, amount);

        return updateBills(credit, debit);
    }

    @Override
    public List<Bill> calcCredit(int creditId, int debitId, long amount) {
        Bill credit = getBill(creditId);
        Bill debit = getBill(debitId);

        credit.setAmount(credit.getAmount() + amount);
        debit.setAmount(debit.getAmount() - amount);

        logActions(creditId, debitId, amount);

        return updateBills(credit, debit);
    }

    @Override
    public void importBills(List<Bill> importedBills) {
        List<Bill> bills = getAll();

        AtomicReference<Boolean> isPresent = new AtomicReference<>(false);

        importedBills.forEach(iB -> {
            if (!isPresent.get()) {
                bills.stream()
                    .filter(bill -> bill.getId() == iB.getId())
                    .forEach(bill -> {
                        if (bill.getAmount() != iB.getAmount()) {
                            updateBill(iB.getId(), iB.getCustomer().getId(), iB.getAmount());
                        }
                        log.info(String.format(
                            "updateBill - ID: %s, CustomerName: %s, Amount: %s",
                            iB.getId(), iB.getCustomer().getId(), iB.getAmount()));
                        isPresent.set(true);
                    });
            }
            if (!isPresent.get()) {
                bills.stream()
                    .filter(bill -> bill.getId() != iB.getId())
                    .forEach(bill -> {
                        log.info(String.format(
                                "addBill - ID: %s, CustomerName: %s, Amount: %s",
                                iB.getId(), iB.getCustomer().getId(), iB.getAmount()));
                        isPresent.set(true);
                        billRepository.addBill(iB.getCustomer().getId(), iB.getAmount());
                    });
            }
        });
    }

    private List<Bill> updateBills(Bill credit, Bill debit) {
        List<Bill> billList = new ArrayList<>();
        billList.add(credit);
        billList.add(debit);

        updateBill(credit);
        updateBill(debit);
        return billList;
    }

    private void logActions(int creditId, int debitId, long amount) {
        LocalDateTime dateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        actionRepository.addAction(dateTime, creditId, ActionType.CREDIT, (int) amount);
        actionRepository.addAction(dateTime, debitId, ActionType.DEBIT, (int) amount);
    }

}
