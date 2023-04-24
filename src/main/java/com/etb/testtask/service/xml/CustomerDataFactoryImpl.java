package com.etb.testtask.service.xml;

import com.etb.testtask.model.Customer;
import com.etb.testtask.model.CustomerData;
import com.etb.testtask.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
public class CustomerDataFactoryImpl {

    private final CustomerService customerService;
    private final CustomerDataService customerDataService;
    private final CustomerDataMarshaller customerDataMarshaller;

    public String exportCustomerData() {
        CustomerData customerData = customerDataService.exportData();
        return customerDataMarshaller.marshallRequest(customerData, CustomerData.class);
    }

    public List<Customer> importCustomerData(String data) {
        CustomerData customerData = (CustomerData) customerDataMarshaller.unmarshallResponse(data, CustomerData.class);
        customerDataService.importData(customerData);
        return customerService.getAll();
    }

}
