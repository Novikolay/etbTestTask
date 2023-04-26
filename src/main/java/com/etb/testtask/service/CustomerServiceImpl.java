package com.etb.testtask.service;

import com.etb.testtask.repository.CustomerRepository;
import com.etb.testtask.model.Customer;
import com.etb.testtask.utils.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Log
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

        AtomicReference<Boolean> isPresent = new AtomicReference<>(false);
        importedCustomers.forEach(iC -> {
            if (!isPresent.get()) {
                customers.stream()
                    .filter(customer -> customer.getId() == iC.getId())
                    .forEach(customer -> {
                        if (!Objects.equals(customer.getName(), iC.getName())) {
                            customerRepository.updateCustomer(iC.getId(), iC.getName());
                        }
                        log.info(String.format(
                                "equals - ID: %s, CustomerName: %s",
                                customer.getId(), customer.getName()));
                        isPresent.set(true);
                    });
            }
            if (!isPresent.get()) {
                customers.stream()
                    .filter(customer -> customer.getId() != iC.getId())
                    .forEach(customer -> {
                        log.info(String.format(
                                "addCustomer forEach - ID: %s, CustomerName: %s",
                                iC.getId(), iC.getName()));
                        isPresent.set(true);
                        customerRepository.addCustomer(customer.getId(), customer.getName());
                    });
            }
        });
    }

}
