package com.ro.controllers;

import com.ro.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class PaymentsController {

    @PostMapping("/payment")
    public ResponseEntity<Payment> createPayment(
            @RequestHeader String requestId,
            @RequestBody Payment payment) {
        log.info("Received request with Id " + requestId + "; Payment Amount: " + payment.getAmount());

        payment.setId(UUID.randomUUID().toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("requestId", requestId)
                .body(payment);
    }
}
