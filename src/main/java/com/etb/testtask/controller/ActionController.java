package com.etb.testtask.controller;

import com.etb.testtask.model.Action;
import com.etb.testtask.service.ActionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/actions")
@AllArgsConstructor
public class ActionController {

    private final ActionService actionService;

    @GetMapping(value = "/all")
    public List<Action> getAll() {
        return actionService.getAll();
    }
}
