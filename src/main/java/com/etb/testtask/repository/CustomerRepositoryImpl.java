package com.etb.testtask.repository;

import com.etb.testtask.dto.CustomerMapper;
import com.etb.testtask.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private static final String SQL_GET_CUSTOMER_BY_ID =
            "select id, name from customers where id = :id";
    private static final String SQL_GET_ALL_CUSTOMERS =
            "select * from customers";
    private static final String SQL_GET_UPDATE_CUSTOMER =
            "update customers set name = :name where id = :id";
    private static final String SQL_GET_ADD_CUSTOMER =
            "insert into customers (id, name) values (NEXTVAL('customers_id_seq'), :name)";

    private final CustomerMapper customerMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Customer> getById(int id) {
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.query(
                        SQL_GET_CUSTOMER_BY_ID,
                        params,
                        customerMapper
                ).stream()
                .findFirst();
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(jdbcTemplate.query(
                SQL_GET_ALL_CUSTOMERS,
                customerMapper
        ));
    }

    @Override
    public void updateCustomer(int id, String name) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("id", id);
        jdbcTemplate.update(SQL_GET_UPDATE_CUSTOMER, params);
    }

    @Override
    public void addCustomer(int id, String name) {
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("name", name);
        jdbcTemplate.update(SQL_GET_ADD_CUSTOMER, params);
    }
}
