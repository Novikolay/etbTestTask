package com.etb.testtask.repository;

import com.etb.testtask.model.Action;
import com.etb.testtask.model.ActionType;

import java.time.LocalDateTime;
import java.util.List;

public interface ActionRepository {
    void addAction(LocalDateTime dateTime, int billId, ActionType action, int amount);
    List<Action> getAll();
}
