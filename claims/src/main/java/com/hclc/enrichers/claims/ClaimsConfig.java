package com.hclc.enrichers.claims;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "config")
public class ClaimsConfig {
    private long simulatedProcessingTimeMillis;
}
