package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Order;
import com.lambda.orders.demo.models.Payment;
import com.lambda.orders.demo.repositories.CustomersRepository;
import com.lambda.orders.demo.repositories.OrdersRepository;
import com.lambda.orders.demo.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PaymentRepository payrepos;

    @Autowired
    private CustomersRepository custrepos;

    @Transactional

    @Override
    public Order save(Order order) {
        Order newOrder = new Order();
        long oldid = order.getOrdnum();

        if (oldid != 0) {
            ordersRepository.findById(oldid)
                    .orElseThrow(() -> new EntityNotFoundException("Order " + oldid + " not found!"));

            newOrder.setOrdnum(oldid);
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.setCustomer(custrepos.findById(order.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " not found!")));

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()) {
            Payment newPayment = payrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));

            newOrder.getPayments().add(newPayment);
        }

        return ordersRepository.save(newOrder);
    }

    @Override
    public Order findOrderById(long ordnum) {
        Order order = ordersRepository.findById(ordnum)
                .orElseThrow(() -> new EntityNotFoundException("Order " + ordnum + " not found!"));
        return order;
    }

    @Override
    public Order update(long id, Order order) {
        Order newOrder = ordersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));

        if (order.getOrderdescription() != null) {
            newOrder.setOrderdescription(order.getOrderdescription());
        }
        if (order.hasvalueforordamount) {
            newOrder.setOrdamount(order.getOrdamount());
        }
        if (order.hasvalueforadvanceamount) {
            newOrder.setAdvanceamount(order.getAdvanceamount());
        }
        if (order.getCustomer() != null) {
            newOrder.setCustomer(order.getCustomer());
        }
        if (order.getPayments().size() > 0) {
            newOrder.getPayments().clear();
            for (Payment p : order.getPayments()) {
                Payment newPayment = payrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));

                newOrder.getPayments().add(newPayment);
            }
        }


        return newOrder;
    }

    @Transactional
    @Override
    public void delete(long id) {
        ordersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found!"));

        ordersRepository.deleteById(id);
    }
}
