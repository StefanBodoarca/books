package com.ro.clients.proxy;

import com.ro.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class PaymentProxy {
    private final WebClient webClient;

    @Value("${name.service.url}")
    private String paymentsServiceUrl;

    public PaymentProxy(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * The method returns a Mono, allowing another functionality to subscribe to it.
     */
    public Mono<Payment> createPayment(
            String requestId,
            Payment payment) {
        String uri = paymentsServiceUrl + "/payment";

        return webClient.post()
                .uri(uri)
                .header("requestId", requestId)
                .body(Mono.just(payment), Payment.class) //send a Mono. This way, we can create an independent task that provides the request body value. The WebClient subscribed to this task becomes dependent on it.
                .retrieve()
                .bodyToMono(Payment.class);
    }
}
