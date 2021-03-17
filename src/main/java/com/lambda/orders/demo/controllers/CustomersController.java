package com.lambda.orders.demo.controllers;

import com.lambda.orders.demo.models.Customer;
import com.lambda.orders.demo.services.CustomerServices;
import com.lambda.orders.demo.views.CustomerOrderCount;
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
@RequestMapping(value = "/customers")
public class CustomersController {
    @Autowired
    private CustomerServices customerServices;

    // http://localhost:2019/customers/orders
    @GetMapping(value="orders", produces = "application/json")
    public ResponseEntity<?> findCustomerOrders() {
        List<Customer> customerOrders = customerServices.findCustomerOrders();
        return new ResponseEntity<>(customerOrders, HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer/{custcode}
    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custcode) {
        Customer customer = customerServices.findCustomerById(custcode);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    // http://localhost:2019/customers/namelike/{subname}
    @GetMapping(value = "namelike/{subname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String subname) {
        List<Customer> customers = customerServices.findCustomerByNameLike(subname);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // http://localhost:2019/customers/orders/count
    @GetMapping(value = "orders/count", produces = "application/json")
    public ResponseEntity<?> findCustomerOrderCounts() {
        List<CustomerOrderCount> custOrderCounts = customerServices.findCustomerOrderCounts();
        return new ResponseEntity<>(custOrderCounts, HttpStatus.OK);
    }

    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer customer) {
        customer.setCustcode(0);
        customerServices.save(customer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(customer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> replaceCustomerById(@Valid @RequestBody Customer customer, @PathVariable long custcode) {
        customer.setCustcode(custcode);
        customerServices.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> updateCustomerById(@RequestBody Customer customer, @PathVariable long custcode) {
        customerServices.update(custcode, customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode) {
        customerServices.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
