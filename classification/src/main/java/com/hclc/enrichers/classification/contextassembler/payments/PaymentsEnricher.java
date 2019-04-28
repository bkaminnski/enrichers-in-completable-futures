package com.hclc.enrichers.classification.contextassembler.payments;

import com.hclc.enrichers.classification.contextassembler.Enricher;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.contextassembler.ThrowableEnrichment;
import com.hclc.enrichers.classification.providers.payments.PaymentsProvider;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentsEnricher implements Enricher {
    private final PaymentsProvider paymentsProvider;

    public PaymentsEnricher(PaymentsProvider paymentsProvider) {
        this.paymentsProvider = paymentsProvider;
    }

    public Enrichment run(String customerId) {
        return Try
                .of(() -> paymentsProvider.provideFor(customerId))
                .onFailure(t -> log.warn("Exception occurred in payments enricher for customer id {}.", customerId, t))
                .fold(ThrowableEnrichment::new, PaymentsEnrichment::new);
    }
}
