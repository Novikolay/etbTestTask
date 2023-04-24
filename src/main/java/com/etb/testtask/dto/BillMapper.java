package com.etb.testtask.dto;

import com.etb.testtask.model.Bill;
import com.etb.testtask.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class BillMapper implements RowMapper<Bill> {

    private CustomerService customerService;

    @Override
    public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Bill(
                rs.getInt("id"),
                customerService.getCustomer(rs.getInt("customerId")),
                rs.getLong("amount")
        );
    }
}