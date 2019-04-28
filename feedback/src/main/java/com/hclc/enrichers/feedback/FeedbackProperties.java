package com.hclc.enrichers.feedback;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "feedback")
public class FeedbackProperties {
    private long simulatedProcessingTimeMillis;
}
