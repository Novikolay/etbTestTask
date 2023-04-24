package com.etb.testtask.service;

import com.etb.testtask.model.Bill;
import com.etb.testtask.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomer(int customerId);
    List<Customer> getAll();
    void importCustomers(List<Customer> importedCustomers);
}
