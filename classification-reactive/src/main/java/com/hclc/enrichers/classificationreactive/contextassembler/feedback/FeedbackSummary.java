package com.hclc.enrichers.classificationreactive.contextassembler.feedback;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackSummary {
    private FeedbackType feedbackType;
    private long numberOfAllFeedbackRequests;
    private long numberOfIgnoredFeedbacks;
    private long numberOfAbortedFeedbacks;
    private long numberOfFinishedFeedbacks;
}
