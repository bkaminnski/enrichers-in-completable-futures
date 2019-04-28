package com.hclc.enrichers.classification.contextassembler;

import com.hclc.enrichers.classification.contextassembler.claims.ClaimsSummary;
import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackSummary;
import com.hclc.enrichers.classification.contextassembler.payments.PaymentsSummary;
import lombok.Data;

import java.util.List;

@Data
public class Context {
    private ClaimsSummary claims;
    private PaymentsSummary payments;
    private List<FeedbackSummary> feedback;
}
