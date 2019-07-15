package com.hclc.enrichers.classificationreactive.config;

import org.springframework.boot.actuate.metrics.web.client.MetricsRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, MetricsRestTemplateCustomizer metricsRestTemplateCustomizer) {
        return restTemplateBuilder
                .additionalCustomizers(metricsRestTemplateCustomizer)
                .build();
    }
}
