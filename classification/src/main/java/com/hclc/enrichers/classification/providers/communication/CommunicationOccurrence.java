package com.hclc.enrichers.classification.providers.communication;

import lombok.Data;

import java.util.Date;

@Data
public class CommunicationOccurrence {
    private String id;
    private Date occurredAt;
    private String comment;
    private CommunicationOccurrenceType type;
}
