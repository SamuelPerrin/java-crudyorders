package com.lambda.orders.demo.repositories;

import com.lambda.orders.demo.models.Customer;
import com.lambda.orders.demo.views.CustomerOrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByCustnameContainingIgnoringCase(String subname);

    @Query(value = "SELECT c.custname, count(o.ordnum) as ordercount " +
            "FROM customers c LEFT JOIN orders o " +
            "ON c.custcode = o.custcode " +
            "GROUP BY c.custname", nativeQuery = true)
    List<CustomerOrderCount> findCustomerOrderCounts();
}
