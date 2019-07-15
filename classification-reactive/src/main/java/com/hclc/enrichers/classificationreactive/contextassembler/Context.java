package com.hclc.enrichers.classificationreactive.contextassembler;

import com.hclc.enrichers.classificationreactive.contextassembler.claims.ClaimsSummary;
import com.hclc.enrichers.classificationreactive.contextassembler.feedback.FeedbackSummary;
import com.hclc.enrichers.classificationreactive.contextassembler.payments.PaymentsSummary;

import java.util.LinkedList;
import java.util.List;

public class Context {
    private String customerId;
    private ClaimsSummary claims;
    private PaymentsSummary payments;
    private List<FeedbackSummary> feedback;
    private List<com.hclc.enrichers.classificationreactive.contextassembler.AssemblyError> assemblyErrors = new LinkedList<>();

    public Context(String customerId) {
        this.customerId = customerId;
    }

    public Context(String customerId, ClaimsSummary claims, PaymentsSummary payments) {
        this.customerId = customerId;
        this.claims = claims;
        this.payments = payments;
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

    public List<com.hclc.enrichers.classificationreactive.contextassembler.AssemblyError> getAssemblyErrors() {
        return assemblyErrors;
    }

    public Context addAssemblyErrors(AssemblyError assemblyError) {
        this.assemblyErrors.add(assemblyError);
        return this;
    }
}
