package com.etb.testtask.controller;

import com.etb.testtask.model.Bill;
import com.etb.testtask.service.BillService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/bills")
@AllArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping(value = "/{billId:\\d+}")
    public Bill getBill(@PathVariable int billId) {
        return billService.getBill(billId);
    }

    @GetMapping(value = "/all")
    public List<Bill> getAll() {
        return billService.getAll();
    }


    @ApiOperation(
            value = "Рассчет дебета суммы",
            notes = "Кредитный счет - | Дебетовый + "
    )
    @PostMapping(value = "/calcDebit")
    public ResponseEntity<List<Bill>> calcDebit(
            @RequestParam(name = "creditId") Integer creditId,
            @RequestParam(name = "debitId") Integer debitId,
            @RequestParam(name = "amount") Integer amount) {
        log.info("CalcDebit by creditId: {}, debitId: {} with amount: {}", creditId, debitId, amount);
        return ResponseEntity.ok(billService.calcDebit(creditId, debitId, amount));
    }

    @ApiOperation(
            value = "Рассчет кредита суммы",
            notes = "Кредитный счет + | Дебетовый - "
    )
    @PostMapping(value = "/calcCredit")
    public ResponseEntity<List<Bill>> calcCredit(
            @RequestParam(name = "creditId") Integer creditId,
            @RequestParam(name = "debitId") Integer debitId,
            @RequestParam(name = "amount") Integer amount) {
        log.info("CalcCredit by creditId: {}, debitId: {} with amount: {}", creditId, debitId, amount);
        return ResponseEntity.ok(billService.calcCredit(creditId, debitId, amount));
    }
}