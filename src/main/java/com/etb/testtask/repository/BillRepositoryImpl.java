package com.etb.testtask.repository;

import com.etb.testtask.dto.BillMapper;
import com.etb.testtask.model.Bill;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class BillRepositoryImpl implements BillRepository {

    private static final String SQL_GET_BILL_BY_ID =
            "select id, customerId, amount from bills where id = :id";
    private static final String SQL_GET_ALL_BILLS =
            "select * from bills";
    private static final String SQL_GET_UPDATE_BILL =
            "update bills set customerId = :customerId, amount = :amount where id = :id";
    private static final String SQL_GET_ADD_BILL =
            "insert into bills (id, customerId, amount) values (NEXTVAL('bills_id_seq'), :customerId, :amount)";

    private final BillMapper billMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Bill> getById(int id) {
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.query(
                        SQL_GET_BILL_BY_ID,
                        params,
                        billMapper
                ).stream()
                .findFirst();
    }

    @Override
    public List<Bill> getAll() {
        return new ArrayList<>(jdbcTemplate.query(
                SQL_GET_ALL_BILLS,
                billMapper
        ));
    }

    @Override
    public void updateBill(int billId, int customerId, long amount) {
        var params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("amount", amount);
        params.addValue("id", billId);
        jdbcTemplate.update(SQL_GET_UPDATE_BILL, params);
    }

    @Override
    public void addBill(int customerId, long amount) {
        var params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("amount", amount);
        jdbcTemplate.update(SQL_GET_ADD_BILL, params);
    }
}
