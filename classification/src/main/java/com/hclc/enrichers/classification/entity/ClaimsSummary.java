package com.hclc.enrichers.classification.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClaimsSummary {
    private int numberOfAllClaims;
    private int numberOfAcceptedClaims;
    private int numberOfRejectedClaims;
}
