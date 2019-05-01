package com.hclc.enrichers.classification.providers.claims;

import com.hclc.enrichers.classification.config.ClassificationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ClaimsProvider {
    private final RestTemplate restTemplate;
    private final ClassificationProperties classificationProperties;

    public ClaimsProvider(RestTemplate restTemplate, ClassificationProperties classificationProperties) {
        this.restTemplate = restTemplate;
        this.classificationProperties = classificationProperties;
    }

    public List<Claim> provideFor(String customerId) {
        ParameterizedTypeReference<List<Claim>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(classificationProperties.getClaimsUri(), GET, null, responseType, customerId).getBody();
    }
}
