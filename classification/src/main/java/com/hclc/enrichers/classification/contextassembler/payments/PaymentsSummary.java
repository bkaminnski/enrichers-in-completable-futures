package com.hclc.enrichers.classification.contextassembler.payments;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentsSummary {
    private int numberOfAllPayments;
    private int numberOfOverduePayments;
}
