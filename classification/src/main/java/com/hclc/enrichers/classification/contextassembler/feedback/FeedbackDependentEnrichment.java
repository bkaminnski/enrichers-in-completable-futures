package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.contextassembler.Context;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrence;
import com.hclc.enrichers.classification.providers.feedback.Feedback;

import java.util.List;

public class FeedbackEnrichment implements Enrichment {
    private final FeedbackSummary feedbackSummary;

    FeedbackEnrichment(List<Feedback> feedbacks, List<CommunicationOccurrence> communicationOccurrences) {
        feedbackSummary = FeedbackSummary.builder().build();
    }

    @Override
    public void applyTo(Context context) {
        context.setFeedback(feedbackSummary);
    }
}
