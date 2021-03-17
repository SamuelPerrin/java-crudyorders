package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Agent;

public interface AgentServices {
    Agent save(Agent agent);

    Agent findAgentById(long agentcode);
}
