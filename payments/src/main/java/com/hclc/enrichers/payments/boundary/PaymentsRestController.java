package com.hclc.enrichers.payments.boundary;

import com.hclc.enrichers.payments.PaymentsConfig;
import com.hclc.enrichers.payments.control.PaymentsRepository;
import com.hclc.enrichers.payments.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/payments")
public class PaymentsRestController {
    private final PaymentsRepository paymentsRepository;
    private final PaymentsConfig paymentsConfig;

    public PaymentsRestController(PaymentsRepository paymentsRepository, PaymentsConfig paymentsConfig) {
        this.paymentsRepository = paymentsRepository;
        this.paymentsConfig = paymentsConfig;
    }

    @GetMapping
    ResponseEntity<List<Payment>> findBy(@RequestParam("customerId") String customerId) throws InterruptedException {
        sleep(paymentsConfig.getSimulatedProcessingTimeMillis());
        return ResponseEntity.ok(paymentsRepository.findByCustomerId(customerId));
    }
}
