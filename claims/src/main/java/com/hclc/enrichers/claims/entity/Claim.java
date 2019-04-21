package com.hclc.enrichers.claims.entity;

import lombok.Value;

import java.util.Date;

@Value
public class Claim {
    private String policyId;
    private Date requestedAt;
    private ClaimState state;
    private String comment;
}
