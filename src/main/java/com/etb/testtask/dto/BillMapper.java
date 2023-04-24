package com.etb.testtask.dto;

import com.etb.testtask.model.Bill;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BillMapper implements RowMapper<Bill> {

    @Override
    public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Bill(
                rs.getInt("id"),
                rs.getInt("customerId"),
                rs.getLong("amount")
        );
    }
}