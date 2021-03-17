package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Customer;
import com.lambda.orders.demo.models.Order;
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
        Customer newCustomer = new Customer();
        long oldId = customer.getCustcode();

        if (oldId != 0) {
            customersRepository.findById(oldId)
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + oldId + " not found!"));
            newCustomer.setCustcode(oldId);
        }

        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setWorkingarea(customer.getWorkingarea());

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()) {
            Order newOrder = new Order(o.getOrdamount(),
                                        o.getAdvanceamount(),
                                        newCustomer,
                                        o.getOrderdescription());

            newCustomer.getOrders().add(newOrder);
        }

        return customersRepository.save(newCustomer);
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

    @Transactional
    @Override
    public Customer update(long id, Customer customer) {
        Customer currentCustomer = customersRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + "not found!"));

        if (customer.getCustcity() != null) {
            currentCustomer.setCustcity(customer.getCustcity());
        }
        if (customer.getCustcountry() != null) {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }
        if (customer.getCustname() != null) {
            currentCustomer.setCustname(customer.getCustname());
        }
        if (customer.getGrade() != null) {
            currentCustomer.setGrade(customer.getGrade());
        }
        if (customer.getPhone() != null) {
            currentCustomer.setPhone(customer.getPhone());
        }
        if (customer.getWorkingarea() != null) {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.hasvalueforopeningamt) {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if (customer.hasvalueforoutstandingamt) {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if (customer.hasvalueforpaymentamt) {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if (customer.hasvalueforreceiveamt) {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.getOrders().size() > 0) {
            currentCustomer.getOrders().clear();
            for (Order o : customer.getOrders()) {
                Order newOrder = new Order(
                        o.getOrdamount(),
                        o.getAdvanceamount(),
                        currentCustomer,
                        o.getOrderdescription()
                );

                currentCustomer.getOrders().add(newOrder);
            }
        }

        return customersRepository.save(currentCustomer);
    }

    @Override
    public void delete(long id) {
        customersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));

        customersRepository.deleteById(id);
    }
}
