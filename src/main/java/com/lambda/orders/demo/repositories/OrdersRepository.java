package com.lambda.orders.demo.repositories;

import com.lambda.orders.demo.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Long> {
}
