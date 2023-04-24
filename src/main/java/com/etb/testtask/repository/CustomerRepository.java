package com.etb.testtask.repository;

import com.etb.testtask.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository  {
    Optional<Customer> getById(int id);
    List<Customer> getAll();

    void updateCustomer(int id, String name);
    void addCustomer(String name);
}
