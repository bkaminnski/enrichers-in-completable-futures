package com.hclc.enrichers.classificationreactive.contextassembler;

import com.hclc.enrichers.classificationreactive.contextassembler.claims.ClaimsSummary;
import com.hclc.enrichers.classificationreactive.contextassembler.feedback.FeedbackSummary;
import com.hclc.enrichers.classificationreactive.contextassembler.payments.PaymentsSummary;

import java.util.List;

public class Context {
    private final String customerId;
    private final ClaimsSummary claims;
    private final PaymentsSummary payments;
    private final List<FeedbackSummary> feedback = null; // not implemented
    private final List<AssemblyError> assemblyErrors;

    public Context(String customerId, ClaimsSummary claims, PaymentsSummary payments, List<AssemblyError> assemblyErrors) {
        this.customerId = customerId;
        this.claims = claims;
        this.payments = payments;
        this.assemblyErrors = assemblyErrors;
    }

    public String getCustomerId() {
        return customerId;
    }

    public ClaimsSummary getClaims() {
        return claims;
    }

    public PaymentsSummary getPayments() {
        return payments;
    }

    public List<FeedbackSummary> getFeedback() {
        return feedback;
    }

    public List<AssemblyError> getAssemblyErrors() {
        return assemblyErrors;
    }
}
