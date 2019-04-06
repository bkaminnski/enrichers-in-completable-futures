package com.hclc.enrichers.claims.entity;

import java.util.Date;

public class Claim {
    private String policyId;
    private Date requestedAt;
    private ClaimState state;
    private String comment;

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
