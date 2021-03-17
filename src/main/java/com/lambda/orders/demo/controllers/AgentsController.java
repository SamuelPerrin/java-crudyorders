package com.lambda.orders.demo.controllers;

import com.lambda.orders.demo.models.Agent;
import com.lambda.orders.demo.services.AgentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentsController {
    @Autowired
    private AgentServices agentServices;

    // http://localhost:2019/agents/agent/{agentcode}
    @GetMapping(value = "/agents/agent/{agentcode}", produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable long agentcode) {
        Agent agent = agentServices.findAgentById(agentcode);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }
}
