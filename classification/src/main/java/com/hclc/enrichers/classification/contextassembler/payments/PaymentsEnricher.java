package com.hclc.enrichers.classification.contextassembler.payments;

import com.hclc.enrichers.classification.contextassembler.Enricher;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.providers.payments.PaymentsProvider;
import org.springframework.stereotype.Component;

@Component
public class PaymentsEnricher implements Enricher {
    private final PaymentsProvider paymentsProvider;

    public PaymentsEnricher(PaymentsProvider paymentsProvider) {
        this.paymentsProvider = paymentsProvider;
    }

    public Enrichment run(String customerId) {
        return new PaymentsEnrichment(paymentsProvider.provideFor(customerId));
    }
}
