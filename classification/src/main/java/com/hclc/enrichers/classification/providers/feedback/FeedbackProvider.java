package com.hclc.enrichers.classification.providers.feedback;

import com.hclc.enrichers.classification.ClassificationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
public class FeedbackProvider {
    private final RestTemplate restTemplate;
    private final ClassificationProperties classificationProperties;

    public FeedbackProvider(RestTemplate restTemplate, ClassificationProperties classificationProperties) {
        this.restTemplate = restTemplate;
        this.classificationProperties = classificationProperties;
    }

    public List<Feedback> provideFor(String customerId) {
        String uri = fromUriString(classificationProperties.getFeedbackUri())
                .queryParam("customerId", customerId)
                .toUriString();
        ParameterizedTypeReference<List<Feedback>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(uri, GET, null, responseType).getBody();
    }
}
