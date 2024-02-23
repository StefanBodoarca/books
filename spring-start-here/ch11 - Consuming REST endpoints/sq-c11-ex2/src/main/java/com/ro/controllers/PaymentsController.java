package com.ro.controllers;

import com.ro.clients.proxy.PaymentProxy;
import com.ro.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class PaymentsController {
    private final PaymentProxy paymentProxy;

    public PaymentsController(PaymentProxy paymentProxy) {
        this.paymentProxy = paymentProxy;
    }

    @PostMapping("/payment")
    public Payment createPayment(
            @RequestBody Payment payment) {
        return paymentProxy.createPayment(payment);
    }
}
