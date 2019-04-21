package com.hclc.enrichers.classification.contextassembler;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContextAssembler {
    private final List<Enricher> enrichers;

    public ContextAssembler(List<Enricher> enrichers) {
        this.enrichers = enrichers;
    }

    public Context assembleFor(String customerId) {
        Context context = new Context();
        enrichers.stream()
                .map(enricher -> enricher.run(customerId))
                .forEach(enrichment -> enrichment.applyTo(context));
        return context;
    }
}
