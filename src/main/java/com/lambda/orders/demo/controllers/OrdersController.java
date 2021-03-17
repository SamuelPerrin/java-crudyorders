package com.lambda.orders.demo.controllers;

import com.lambda.orders.demo.models.Order;
import com.lambda.orders.demo.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    @Autowired
    private OrderServices orderServices;

    // http://localhost:2019/orders/order/{ordnum}
    @GetMapping(value = "orders/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordnum) {
        Order order = orderServices.findOrderById(ordnum);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
