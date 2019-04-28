package com.hclc.enrichers.communication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "config")
public class CommunicationConfig {
    private long simulatedProcessingTimeMillis;
}
