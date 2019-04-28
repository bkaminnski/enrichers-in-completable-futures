package com.hclc.enrichers.classification.providers.claims;

import com.hclc.enrichers.classification.ClassificationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
public class ClaimsProvider {
    private final RestTemplate restTemplate;
    private final ClassificationProperties classificationProperties;

    public ClaimsProvider(RestTemplate restTemplate, ClassificationProperties classificationProperties) {
        this.restTemplate = restTemplate;
        this.classificationProperties = classificationProperties;
    }

    public List<Claim> provideFor(String customerId) {
        String uri = fromUriString(classificationProperties.getClaimsUri())
                .queryParam("customerId", customerId)
                .toUriString();
        ParameterizedTypeReference<List<Claim>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(uri, GET, null, responseType).getBody();
    }
}
