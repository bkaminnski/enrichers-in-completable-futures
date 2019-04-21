package com.hclc.enrichers.classification.contextassembler.claims;

import com.hclc.enrichers.classification.contextassembler.Enricher;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.providers.claims.ClaimsProvider;
import org.springframework.stereotype.Component;

@Component
public class ClaimsEnricher implements Enricher {
    private final ClaimsProvider claimsProvider;

    public ClaimsEnricher(ClaimsProvider claimsProvider) {
        this.claimsProvider = claimsProvider;
    }

    public Enrichment run(String customerId) {
        return new ClaimsEnrichment(claimsProvider.provideFor(customerId));
    }
}
