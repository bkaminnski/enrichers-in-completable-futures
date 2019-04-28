package com.hclc.enrichers.classification.providers.communication;

import com.hclc.enrichers.classification.ClassificationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
public class CommunicationOccurrencesProvider {
    private final RestTemplate restTemplate;
    private final ClassificationProperties classificationProperties;

    public CommunicationOccurrencesProvider(RestTemplate restTemplate, ClassificationProperties classificationProperties) {
        this.restTemplate = restTemplate;
        this.classificationProperties = classificationProperties;
    }

    public List<CommunicationOccurrence> provideFor(String customerId) {
        String uri = fromUriString(classificationProperties.getCommunicationUri())
                .queryParam("customerId", customerId)
                .toUriString();
        ParameterizedTypeReference<List<CommunicationOccurrence>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(uri, GET, null, responseType).getBody();
    }
}
