package com.hclc.enrichers.claims.entity;

import java.util.Date;

public class Claim {
    private final String policyId;
    private final Date requestedAt;
    private final ClaimState state;
    private final String comment;

    public Claim(String policyId, Date requestedAt, ClaimState state, String comment) {
        this.policyId = policyId;
        this.requestedAt = requestedAt;
        this.state = state;
        this.comment = comment;
    }

    public String getPolicyId() {
        return policyId;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public ClaimState getState() {
        return state;
    }

    public String getComment() {
        return comment;
    }
}
