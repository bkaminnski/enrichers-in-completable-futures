package com.hclc.enrichers.classificationreactive.providers.payments;

import com.hclc.enrichers.classificationreactive.config.ClassificationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
@Slf4j
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

    public Mono<List<Payment>> provideObservableFor(String customerId) {
        log.info("Requesting payments in thread {}", Thread.currentThread().getId());

        ParameterizedTypeReference<List<Payment>> responseType = new ParameterizedTypeReference<>() {
        };

        return WebClient.create("http://localhost:8005/payments?customerId=" + customerId)
                .get()
                .exchange()
                .flatMap(resp -> resp.bodyToMono(responseType));
    }
}
