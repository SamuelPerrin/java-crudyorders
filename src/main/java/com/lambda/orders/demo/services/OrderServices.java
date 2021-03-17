package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Order;

import java.util.List;

public interface OrderServices {
    Order save(Order order);

    Order findOrderById(long ordnum);

    Order update(long id, Order order);

    List<Order> findAllOrders();

    void delete(long id);
}
