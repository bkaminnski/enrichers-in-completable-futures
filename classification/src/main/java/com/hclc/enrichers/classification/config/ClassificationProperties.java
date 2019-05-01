package com.hclc.enrichers.classification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "classification")
public class ClassificationProperties {
    private String claimsUri;
    private String communicationUri;
    private String feedbackUri;
    private String paymentsUri;
    private long assemblyTimeoutMillis;
    private int enrichersThreadPoolCorePoolSize;
    private int enrichersThreadPoolMaxPoolSize;
}
