package com.hclc.enrichers.communication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "communication")
public class CommunicationProperties {
    private long simulatedProcessingTimeMillis;
}
