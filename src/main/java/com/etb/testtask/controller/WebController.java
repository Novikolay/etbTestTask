package com.etb.testtask.controller;

import com.etb.testtask.model.Customer;
import com.etb.testtask.model.RestResponse;
import com.etb.testtask.service.ActionService;
import com.etb.testtask.service.BillService;
import com.etb.testtask.service.CustomerService;
import com.etb.testtask.service.xml.CustomerDataFactory;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class WebController {

    private BillService billService;
    private CustomerService customerService;
    private ActionService actionService;
    private CustomerDataFactory customerDataFactory;

    @GetMapping("/")
    public String root() {
        return "redirect:info";
    }

    @RequestMapping(value = "/bills", method = RequestMethod.GET)
    public ModelAndView getBills() {
        return new ModelAndView("main/bills")
                .addObject("customers",
                        customerService.getAll())
                .addObject("bills",
                        billService.getAll());
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ModelAndView getCustomers() {
        return new ModelAndView("main/customers")
                .addObject("customers",
                        customerService.getAll());
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView getActions() {
        return new ModelAndView("main/history")
                .addObject("actions",
                        actionService.getAll());
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView getFullInfo() {
        return new ModelAndView("main/info")
                .addObject("customers",
                        customerService.getAll())
                .addObject("bills",
                        billService.getAll());
    }

    @RequestMapping(value = "/operations", method = RequestMethod.GET)
    public ModelAndView getOperations() {
        return new ModelAndView("main/operations");
    }

    @ApiOperation(
            value = "Рассчет дебета суммы",
            notes = "Кредитный счет - | Дебетовый + "
    )
    @PostMapping(value = "/calcDebit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void calcDebit(
            @RequestParam(name = "creditId") Integer creditId,
            @RequestParam(name = "debitId") Integer debitId,
            @RequestParam(name = "amount") Integer amount) {
        ResponseEntity.ok(billService.calcDebit(creditId, debitId, amount));
    }

    @ApiOperation(
            value = "Рассчет кредита суммы",
            notes = "Кредитный счет + | Дебетовый - "
    )
    @PostMapping(value = "/calcCredit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void calcCredit(
            @RequestParam(name = "creditId") Integer creditId,
            @RequestParam(name = "debitId") Integer debitId,
            @RequestParam(name = "amount") Integer amount) {
        ResponseEntity.ok(billService.calcCredit(creditId, debitId, amount));
    }

    @RequestMapping(value = "/xml", method = RequestMethod.GET)
    public ModelAndView getXmlOperations() {
        return new ModelAndView("main/xmlOperations");
    }

    @ApiOperation(
            value = "Экспорт списка клиентов",
            notes = "XML export"
    )
    @PostMapping(value = "/exportCustomerList")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RestResponse exportCustomerList() {
        return new RestResponse(customerDataFactory.exportCustomerData());
    }

    @ApiOperation(
            value = "Импорт списка клиентов",
            notes = "XML import"
    )
    @PostMapping(value = "/importCustomerList")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<Customer> importCustomerList(@RequestBody String body) {
        return customerDataFactory.importCustomerData(body);
    }

}
