package com.hclc.enrichers.communication.entity;

import java.util.Date;

public class CommunicationOccurrence {
    private final String id;
    private final Date occurredAt;
    private final String comment;
    private final CommunicationOccurrenceType type;

    public CommunicationOccurrence(String id, Date occurredAt, String comment, CommunicationOccurrenceType type) {
        this.id = id;
        this.occurredAt = occurredAt;
        this.comment = comment;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Date getOccurredAt() {
        return occurredAt;
    }

    public String getComment() {
        return comment;
    }

    public CommunicationOccurrenceType getType() {
        return type;
    }
}
