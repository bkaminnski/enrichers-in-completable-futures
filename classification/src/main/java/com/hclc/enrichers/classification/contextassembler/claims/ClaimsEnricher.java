package com.hclc.enrichers.classification.contextassembler.claims;

import com.hclc.enrichers.classification.contextassembler.Enricher;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.contextassembler.ThrowableEnrichment;
import com.hclc.enrichers.classification.providers.claims.ClaimsProvider;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClaimsEnricher implements Enricher {
    private final ClaimsProvider claimsProvider;

    public ClaimsEnricher(ClaimsProvider claimsProvider) {
        this.claimsProvider = claimsProvider;
    }

    public Enrichment run(String customerId) {
        return Try
                .of(() -> claimsProvider.provideFor(customerId))
                .onFailure(t -> log.warn("Exception occurred in claims enricher for customer id {}.", customerId, t))
                .fold(ThrowableEnrichment::new, ClaimsEnrichment::new);
    }
}
