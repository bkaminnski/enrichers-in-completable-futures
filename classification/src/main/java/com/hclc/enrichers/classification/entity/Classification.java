package com.hclc.enrichers.classification.entity;

import lombok.Value;

@Value
public class Classification {
    private String customerId;
    private ClassificationContext context;
    private ClassificationResult result;
}
