package com.hclc.enrichers.classification.providers.feedback;

import lombok.Data;

import java.util.Date;

@Data
public class Feedback {
    private String id;
    private String communicationId;
    private Date occurredAt;
    private FeedbackCollectionType collectionType;
    private FeedbackStatus status;
    private Integer stars;
}
