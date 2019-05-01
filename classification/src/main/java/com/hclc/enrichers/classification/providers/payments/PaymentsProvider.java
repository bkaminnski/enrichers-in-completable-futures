package com.hclc.enrichers.classification.providers.payments;

import com.hclc.enrichers.classification.config.ClassificationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class PaymentsProvider {
    private final RestTemplate restTemplate;
    private final ClassificationProperties classificationProperties;

    public PaymentsProvider(RestTemplate restTemplate, ClassificationProperties classificationProperties) {
        this.restTemplate = restTemplate;
        this.classificationProperties = classificationProperties;
    }

    public List<Payment> provideFor(String customerId) {
        ParameterizedTypeReference<List<Payment>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(classificationProperties.getPaymentsUri(), GET, null, responseType, customerId).getBody();
    }
}
