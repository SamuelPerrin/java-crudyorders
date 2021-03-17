package com.lambda.orders.demo.repositories;

import com.lambda.orders.demo.models.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentsRepository extends CrudRepository<Agent, Long> {
}
