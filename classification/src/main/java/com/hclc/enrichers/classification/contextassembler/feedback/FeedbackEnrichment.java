package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.providers.feedback.Feedback;
import lombok.Value;

import java.util.List;

@Value
public class FeedbackEnrichment {
    private List<Feedback> feedbacks;
    private String customerId;
}
