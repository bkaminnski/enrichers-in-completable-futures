package com.hclc.enrichers.classification.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentsSummary {
    private int numberOfAllPayments;
    private int numberOfOverduePayments;
}
