package com.etb.testtask.service.xml;

import com.etb.testtask.model.Customer;

import java.util.List;

public interface CustomerDataFactory {
    String exportCustomerData();
    List<Customer> importCustomerData(String data);
}
