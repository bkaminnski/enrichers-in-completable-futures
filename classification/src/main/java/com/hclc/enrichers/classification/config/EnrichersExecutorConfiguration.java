package com.hclc.enrichers.classification.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class EnrichersExecutorConfiguration {
    private final ClassificationProperties classificationProperties;

    public EnrichersExecutorConfiguration(ClassificationProperties classificationProperties) {
        this.classificationProperties = classificationProperties;
    }

    @Bean(name = "enrichersExecutor")
    public Executor enrichersExecutor(MeterRegistry registry) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                classificationProperties.getEnrichersThreadPoolCorePoolSize(),
                classificationProperties.getEnrichersThreadPoolMaxPoolSize(),
                5,
                SECONDS,
                new LinkedBlockingQueue<>(),
                new CustomizableThreadFactory("enrichers-")
        );
        return ExecutorServiceMetrics.monitor(registry, threadPoolExecutor, "enrichers");
    }
}
