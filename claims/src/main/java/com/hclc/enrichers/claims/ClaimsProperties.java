package com.hclc.enrichers.claims;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "claims")
public class ClaimsProperties {
    private long simulatedProcessingTimeMillis;
}
