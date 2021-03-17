package com.lambda.orders.demo.controllers;

import com.lambda.orders.demo.models.Order;
import com.lambda.orders.demo.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrdersController {
    @Autowired
    private OrderServices orderServices;

    // http://localhost:2019/orders/order/{ordnum}
    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordnum) {
        Order order = orderServices.findOrderById(ordnum);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> findAllOrders() {
        List<Order> orders = orderServices.findAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order order) {
        order.setOrdnum(0);
        orderServices.save(order);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ordnum}")
                .buildAndExpand(order.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{ordnum}", consumes = "application/json")
    public ResponseEntity<?> replaceOrderById(@Valid @RequestBody Order order, @PathVariable long ordnum) {
        order.setOrdnum(ordnum);
        orderServices.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum) {
        orderServices.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
