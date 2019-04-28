package com.hclc.enrichers.payments;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "config")
public class PaymentsConfig {
    private long simulatedProcessingTimeMillis;
}
