package com.hclc.enrichers.feedback.entity;

import java.util.Date;

public class Feedback {
    private final String id;
    private final String communicationId;
    private final Date occurredAt;
    private final FeedbackCollectionType collectionType;
    private final FeedbackStatus status;
    private final Integer stars;

    public Feedback(String id, String communicationId, Date occurredAt, FeedbackCollectionType collectionType, FeedbackStatus status, Integer stars) {
        this.id = id;
        this.communicationId = communicationId;
        this.occurredAt = occurredAt;
        this.collectionType = collectionType;
        this.status = status;
        this.stars = stars;
    }

    public String getId() {
        return id;
    }

    public String getCommunicationId() {
        return communicationId;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public FeedbackCollectionType getCollectionType() {
        return collectionType;
    }

    public FeedbackStatus getStatus() {
        return status;
    }

    public Integer getStars() {
        return stars;
    }
}
