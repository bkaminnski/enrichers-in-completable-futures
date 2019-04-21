package com.hclc.enrichers.classification.contextassembler.claims;

import com.hclc.enrichers.classification.contextassembler.Context;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.providers.claims.Claim;

import java.util.List;

public class ClaimsEnrichment implements Enrichment {
    private final ClaimsSummary claimsSummary;

    ClaimsEnrichment(List<Claim> claims) {
        claimsSummary = ClaimsSummary.builder()
                .numberOfAllClaims(claims.size())
                .numberOfAcceptedClaims((int) claims.stream().filter(Claim::isAccepted).count())
                .numberOfRejectedClaims((int) claims.stream().filter(Claim::isRejected).count())
                .build();
    }

    @Override
    public void applyTo(Context context) {
        context.setClaims(claimsSummary);
    }
}
