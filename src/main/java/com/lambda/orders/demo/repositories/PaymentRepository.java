package com.lambda.orders.demo.repositories;

import com.lambda.orders.demo.models.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
