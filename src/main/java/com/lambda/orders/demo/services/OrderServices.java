package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Order;

public interface OrderServices {
    Order save(Order order);

    Order findOrderById(long ordnum);

    Order update(long id, Order order);

    void delete(long id);
}
