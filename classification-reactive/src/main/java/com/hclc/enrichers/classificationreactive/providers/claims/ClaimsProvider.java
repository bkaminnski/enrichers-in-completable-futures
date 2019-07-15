package com.hclc.enrichers.classificationreactive.providers.claims;

import com.hclc.enrichers.classificationreactive.config.ClassificationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
@Slf4j
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

    public Mono<List<Claim>> provideObservableFor(String customerId) {
        log.info("Requesting claims in thread {}", Thread.currentThread().getId());

        ParameterizedTypeReference<List<Claim>> responseType = new ParameterizedTypeReference<>() {};

        return WebClient.create("http://localhost:8002/claims?customerId=" + customerId)
                .get()
                .exchange()
                .flatMap(resp -> resp.bodyToMono(responseType));
    }

}
