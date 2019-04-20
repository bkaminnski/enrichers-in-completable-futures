package com.hclc.enrichers.classification.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackSummary {
    private FeedbackType feedbackType;
    private int numberOfAllFeedbackRequests;
    private int numberOfIgnoredFeedbacks;
    private int numberOfAbortedFeedbacks;
    private int numberOfFinishedFeedbacks;
}
