package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.contextassembler.ThrowableEnrichment;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrencesProvider;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeedbackDependentEnricher {
    private final CommunicationOccurrencesProvider communicationOccurrencesProvider;

    public FeedbackDependentEnricher(CommunicationOccurrencesProvider communicationOccurrencesProvider) {
        this.communicationOccurrencesProvider = communicationOccurrencesProvider;
    }

    public Enrichment run(Either<Throwable, FeedbackEnrichment> feedbackEnrichment) {
        return feedbackEnrichment.fold(ThrowableEnrichment::new, this::findCommunications);
    }

    private Enrichment findCommunications(FeedbackEnrichment feedbackEnrichment) {
        return Try
                .of(() -> communicationOccurrencesProvider.provideFor(feedbackEnrichment.getCustomerId()))
                .onFailure(t -> log.warn("Exception occurred in feedback enricher 2/2 for customer id {}.", feedbackEnrichment.getCustomerId(), t))
                .fold(ThrowableEnrichment::new, communicationOccurrences -> new FeedbackDependentEnrichment(feedbackEnrichment.getFeedbacks(), communicationOccurrences));
    }
}
