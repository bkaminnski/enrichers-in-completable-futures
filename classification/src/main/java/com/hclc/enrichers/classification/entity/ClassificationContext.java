package com.hclc.enrichers.classification.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClassificationContext {
    private ClaimsSummary claims;
    private PaymentsSummary payments;
    private FeedbackSummary feedback;
}
