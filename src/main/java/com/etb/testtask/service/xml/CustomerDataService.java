package com.etb.testtask.service.xml;

import com.etb.testtask.model.Bill;
import com.etb.testtask.model.Customer;
import com.etb.testtask.model.CustomerData;
import com.etb.testtask.service.BillService;
import com.etb.testtask.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerDataService {

    private CustomerService customerService;
    private BillService billService;

    public void importData(CustomerData customerData) {
        List<Customer> customers = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();

        customerData.getCustomerList().forEach(c -> {
            customers.add(new Customer(c.getId(), c.getName()));

            c.getBillList().forEach(b -> {
                bills.add(new Bill(b.getId(), customerService.getCustomer(c.getId()), b.getAmount()));
            });
        });

        customerService.importCustomers(customers);
        billService.importBills(bills);
    }

    public CustomerData exportData() {
        CustomerData customerData = new CustomerData();

        List<Customer> customers = customerService.getAll();
        List<Bill> bills = billService.getAll();

        customerData.setCustomerList(new ArrayList<>());

        customers.forEach(c -> {
            CustomerData.CustomerScheme customer = new CustomerData.CustomerScheme();
            customer.setId(c.getId());
            customer.setName(c.getName());
            customer.setBillList(bills.stream()
                    .filter(bill -> c.getId() == bill.getCustomer().getId())
                    .map(this::convertBill)
                    .collect(Collectors.toList()));
            customerData.getCustomerList().add(customer);
        });

        log.info("customerData: {}", customerData);

        return customerData;
    }

    private CustomerData.CustomerScheme.BillScheme convertBill(Bill b) {
        CustomerData.CustomerScheme.BillScheme bill = new CustomerData.CustomerScheme.BillScheme();
        bill.setId(b.getId());
        bill.setAmount(b.getAmount());
        return bill;
    }

}
