package com.etb.testtask.service.xml;

import com.etb.testtask.model.Customer;

import java.util.List;

public interface CustomerDataFactory {
    String createCustomerData();
    List<Customer> importCustomerData(String data);
}
