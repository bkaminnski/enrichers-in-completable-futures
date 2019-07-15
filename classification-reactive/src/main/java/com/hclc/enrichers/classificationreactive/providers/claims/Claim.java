package com.hclc.enrichers.classificationreactive.providers.claims;

import lombok.Data;

import static com.hclc.enrichers.classificationreactive.providers.claims.ClaimState.ACCEPTED;
import static com.hclc.enrichers.classificationreactive.providers.claims.ClaimState.REJECTED;

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
