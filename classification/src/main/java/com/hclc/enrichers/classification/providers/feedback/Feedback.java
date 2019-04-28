package com.hclc.enrichers.feedback.entity;

import lombok.Value;

import java.util.Date;

@Value
public class Feedback {
    private String id;
    private String communicationId;
    private Date occurredAt;
    private FeedbackCollectionType collectionType;
    private FeedbackStatus status;
    private Integer stars;
}
