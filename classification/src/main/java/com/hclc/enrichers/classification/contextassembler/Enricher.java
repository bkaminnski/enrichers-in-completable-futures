package com.hclc.enrichers.classification.contextassembler;

public interface Enricher {
    Enrichment run(String customerId);
}
