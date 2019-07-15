package com.hclc.enrichers.classificationreactive.contextassembler;

import lombok.Getter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.Instant.now;

@Getter
public class AssemblyError {
    private final String threadName;
    private final String message;
    private final ZonedDateTime loggedAt;

    public AssemblyError(String threadName, String message) {
        this.threadName = threadName;
        this.message = message;
        this.loggedAt = now().atZone(ZoneId.of("UTC"));
    }
}
