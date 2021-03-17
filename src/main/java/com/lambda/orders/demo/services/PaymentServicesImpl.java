package com.lambda.orders.demo.services;

import com.lambda.orders.demo.models.Payment;
import com.lambda.orders.demo.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PaymentServicesImpl implements PaymentServices {
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
}
