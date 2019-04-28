package com.hclc.enrichers.classification.contextassembler;

import com.hclc.enrichers.classification.contextassembler.claims.ClaimsSummary;
import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackSummary;
import com.hclc.enrichers.classification.contextassembler.payments.PaymentsSummary;

import java.util.LinkedList;
import java.util.List;

public class Context {
    private String customerId;
    private ClaimsSummary claims;
    private PaymentsSummary payments;
    private List<FeedbackSummary> feedback;
    private List<AssemblyError> assemblyErrors = new LinkedList<>();

    public Context(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ClaimsSummary getClaims() {
        return claims;
    }

    public void setClaims(ClaimsSummary claims) {
        this.claims = claims;
    }

    public PaymentsSummary getPayments() {
        return payments;
    }

    public void setPayments(PaymentsSummary payments) {
        this.payments = payments;
    }

    public List<FeedbackSummary> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<FeedbackSummary> feedback) {
        this.feedback = feedback;
    }

    public List<AssemblyError> getAssemblyErrors() {
        return assemblyErrors;
    }

    public Context addAssemblyErrors(AssemblyError assemblyError) {
        this.assemblyErrors.add(assemblyError);
        return this;
    }
}
