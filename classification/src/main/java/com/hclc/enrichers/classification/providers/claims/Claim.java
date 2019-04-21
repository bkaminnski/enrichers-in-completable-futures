package com.hclc.enrichers.classification.providers.claims;

import lombok.Data;

import static com.hclc.enrichers.classification.providers.claims.ClaimState.ACCEPTED;
import static com.hclc.enrichers.classification.providers.claims.ClaimState.REJECTED;

@Data
public class Claim {
    private ClaimState state;

    public boolean isAccepted() {
        return ACCEPTED == state;
    }

    public boolean isRejected() {
        return REJECTED == state;
    }
}
