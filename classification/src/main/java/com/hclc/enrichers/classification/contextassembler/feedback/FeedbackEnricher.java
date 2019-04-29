package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.providers.feedback.FeedbackProvider;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeedbackEnricher {
    private final FeedbackProvider feedbackProvider;

    public FeedbackEnricher(FeedbackProvider feedbackProvider) {
        this.feedbackProvider = feedbackProvider;
    }

    public Either<Throwable, FeedbackEnrichment> run(String customerId) {
        return Try
                .of(() -> feedbackProvider.provideFor(customerId))
                .map(f -> new FeedbackEnrichment(f, customerId))
                .onFailure(e -> log.warn("Exception occurred in feedback enricher 1/2 for customer id {}.", customerId, e))
                .toEither();
    }
}
