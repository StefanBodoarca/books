package com.ro.service;

import com.ro.exceptions.NotEnoughMoneyException;
import com.ro.model.PaymentDetails;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public PaymentDetails processPayment() {
        throw new NotEnoughMoneyException();
    }
}
