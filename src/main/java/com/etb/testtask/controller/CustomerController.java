package com.etb.testtask.controller;

import com.etb.testtask.model.Customer;
import com.etb.testtask.model.RestResponse;
import com.etb.testtask.service.CustomerService;
import com.etb.testtask.service.xml.CustomerDataFactoryImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDataFactoryImpl customerDataFactory;

    @GetMapping(value = "/{customerId:\\d+}")
    public Customer getCustomer(@PathVariable int customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping(value = "/all")
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @ApiOperation(
            value = "Экспорт списка клиентов",
            notes = "XML export"
    )
    @PostMapping(value = "/exportCustomerList")
    public RestResponse exportCustomerList() {
        return new RestResponse(customerDataFactory.exportCustomerData());
    }

    @ApiOperation(
            value = "Импорт списка клиентов",
            notes = "XML import"
    )
    @PostMapping(value = "/importCustomerList")
    public List<Customer> importCustomerList(@RequestBody String body) {
        return customerDataFactory.importCustomerData(body);
    }

}