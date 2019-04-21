package com.hclc.enrichers.communication.entity;

import lombok.Value;

import java.util.Date;

@Value
public class CommunicationOccurrence {
    private String id;
    private Date occurredAt;
    private String comment;
    private CommunicationOccurrenceType type;
}
