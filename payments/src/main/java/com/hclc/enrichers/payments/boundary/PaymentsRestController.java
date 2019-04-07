package com.hclc.enrichers.payments.boundary;

import com.hclc.enrichers.payments.control.PaymentsRepository;
import com.hclc.enrichers.payments.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentsRestController {

    private final PaymentsRepository paymentsRepository;

    public PaymentsRestController(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @GetMapping
    ResponseEntity<List<Payment>> findBy(@RequestParam("customerId") String customerId) {
        return ResponseEntity.ok(paymentsRepository.findByCustomerId(customerId));
    }
}
