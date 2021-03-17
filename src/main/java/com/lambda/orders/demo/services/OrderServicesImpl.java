package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Order;
import com.lambda.orders.demo.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrdersRepository ordersRepository;

    @Transactional

    @Override
    public Order save(Order order) {
        return ordersRepository.save(order);
    }

    @Override
    public Order findOrderById(long ordnum) {
        Order order = ordersRepository.findById(ordnum)
                .orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " not found!"));
        return order;
    }
}
