package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Agent;
import com.lambda.orders.demo.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class AgentServicesImpl implements AgentServices {
    @Autowired
    private AgentsRepository agentsRepository;

    @Transactional
    @Override
    public Agent save(Agent agent) {
        return agentsRepository.save(agent);
    }

    @Override
    public Agent findAgentById(long agentcode) {
        Agent agent = agentsRepository.findById(agentcode)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + agentcode + " not found!"));
        return agent;
    }
}
