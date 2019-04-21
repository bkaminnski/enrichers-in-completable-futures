package com.hclc.enrichers.classification.providers.payments;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Component
public class PaymentsProvider {
    private final RestTemplate restTemplate;
    private final Environment environment;

    public PaymentsProvider(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public List<Payment> provideFor(String customerId) {
        String uri = fromUriString(environment.getProperty("payments.uri"))
                .queryParam("customerId", customerId)
                .toUriString();
        ParameterizedTypeReference<List<Payment>> responseType = new ParameterizedTypeReference<>() {
        };
        return restTemplate.exchange(uri, GET, null, responseType).getBody();
    }
}
