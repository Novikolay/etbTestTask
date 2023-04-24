package com.etb.testtask.service;

import com.etb.testtask.repository.CustomerRepository;
import com.etb.testtask.model.Customer;
import com.etb.testtask.utils.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Primary
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(int customerId) {
        return customerRepository.getById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(customerId));
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    @Override
    public void importCustomers(List<Customer> importedCustomers) {
        List<Customer> customers = getAll();

        importedCustomers.forEach(iC -> {
            customers.stream()
                    .filter(customer -> customer.getId() == iC.getId())
                    .forEach(customer -> {
                        if (!Objects.equals(customer.getName(), iC.getName())) {
                            customerRepository.updateCustomer(iC.getId(), iC.getName());
                        }
                    });
            customers.stream()
                    .filter(customer -> customer.getId() != iC.getId())
                    .forEach(customer -> {
                        customerRepository.addCustomer(iC.getName());
                    });
        });
    }

}
