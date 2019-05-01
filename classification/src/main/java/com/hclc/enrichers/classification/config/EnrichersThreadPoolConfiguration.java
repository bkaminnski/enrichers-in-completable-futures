package com.hclc.enrichers.classification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class EnrichersThreadPoolConfiguration {
    private final ClassificationProperties classificationProperties;

    public EnrichersThreadPoolConfiguration(ClassificationProperties classificationProperties) {
        this.classificationProperties = classificationProperties;
    }

    @Bean(name = "enrichersThreadPoolExecutor")
    public Executor enrichersThreadPoolExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(classificationProperties.getEnrichersThreadPoolCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(classificationProperties.getEnrichersThreadPoolMaxPoolSize());
        return threadPoolTaskExecutor;
    }
}
