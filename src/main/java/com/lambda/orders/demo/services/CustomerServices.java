package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Customer;
import com.lambda.orders.demo.views.CustomerOrderCount;

import java.util.List;

public interface CustomerServices {
    Customer save(Customer customer);

    Customer findCustomerById(long custcode);

    List<Customer> findCustomerByNameLike(String subname);

    List<Customer> findCustomerOrders();

    List<CustomerOrderCount> findCustomerOrderCounts();
}
