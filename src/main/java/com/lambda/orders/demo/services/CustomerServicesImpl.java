package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Customer;
import com.lambda.orders.demo.repositories.CustomersRepository;
import com.lambda.orders.demo.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    private CustomersRepository customersRepository;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return customersRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(long custcode) {
        Customer customer = customersRepository.findById(custcode)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " not found!"));
        return customer;
    }

    @Override
    public List<Customer> findCustomerByNameLike(String subname) {
        List<Customer> customers = customersRepository.findByCustnameContainingIgnoringCase(subname);
        return customers;
    }

    @Override
    public List<Customer> findCustomerOrders() {
        List<Customer> customers = new ArrayList<>();
        customersRepository.findAll().iterator().forEachRemaining(customers::add);
        return customers;
    }

    @Override
    public List<CustomerOrderCount> findCustomerOrderCounts() {
        List<CustomerOrderCount> custOrderCounts = customersRepository.findCustomerOrderCounts();
        return custOrderCounts;
    }
}
