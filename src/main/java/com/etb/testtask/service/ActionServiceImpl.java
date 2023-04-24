package com.etb.testtask.service;

import com.etb.testtask.model.Action;
import com.etb.testtask.repository.ActionRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@AllArgsConstructor
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;

    @Override
    public List<Action> getAll() {
        return actionRepository.getAll();
    }
}
