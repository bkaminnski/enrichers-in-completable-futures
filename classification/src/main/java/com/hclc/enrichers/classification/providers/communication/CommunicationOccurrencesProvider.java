package com.hclc.enrichers.classification.providers.communication;

import com.hclc.enrichers.classification.config.ClassificationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import static java.lang.String.join;
import static org.springframework.http.HttpMethod.GET;

@Component
public class CommunicationOccurrencesProvider {
    private final RestTemplate restTemplate;
    private final ClassificationProperties classificationProperties;

    public CommunicationOccurrencesProvider(RestTemplate restTemplate, ClassificationProperties classificationProperties) {
        this.restTemplate = restTemplate;
        this.classificationProperties = classificationProperties;
    }

    public List<CommunicationOccurrence> provideFor(Set<String> ids) {
        ParameterizedTypeReference<List<CommunicationOccurrence>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(classificationProperties.getCommunicationUri(), GET, null, responseType, join(",", ids)).getBody();
    }
}
