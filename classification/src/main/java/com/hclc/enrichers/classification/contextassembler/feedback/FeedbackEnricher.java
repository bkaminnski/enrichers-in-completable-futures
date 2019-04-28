package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.providers.feedback.FeedbackProvider;
import org.springframework.stereotype.Component;

@Component
public class FeedbackEnricher {
    private final FeedbackProvider feedbackProvider;

    public FeedbackEnricher(FeedbackProvider feedbackProvider) {
        this.feedbackProvider = feedbackProvider;
    }

    public FeedbackEnrichment run(String customerId) {
        return new FeedbackEnrichment(feedbackProvider.provideFor(customerId), customerId);
    }
}
