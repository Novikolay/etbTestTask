package com.etb.testtask.repository;

import com.etb.testtask.dto.ActionMapper;
import com.etb.testtask.model.Action;
import com.etb.testtask.model.ActionType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class ActionRepositoryImpl implements ActionRepository {
    private static final String SQL_ADD_ACTION =
            "insert into actions (dateTime, billId, actionType, amount) " +
                    "values (:dateTime, :billId, :actionId, :amount)";
    private static final String SQL_GET_ACTION_HISTORY =
            "select * from actions";

    private ActionMapper actionMapper;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addAction(LocalDateTime dateTime, int billId, ActionType action, int amount) {
        var params = new MapSqlParameterSource();
        params.addValue("dateTime", Timestamp.valueOf(dateTime));
        params.addValue("billId", billId);
        params.addValue("actionId", action.getId());
        params.addValue("amount", amount);
        jdbcTemplate.update(SQL_ADD_ACTION, params);
    }

    @Override
    public List<Action> getAll() {
        return new ArrayList<>(jdbcTemplate.query(
                SQL_GET_ACTION_HISTORY,
                actionMapper
        ));
    }


}