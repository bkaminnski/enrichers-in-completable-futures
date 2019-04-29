package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.contextassembler.ThrowableEnrichment;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrence;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrencesProvider;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
        try {
            List<CommunicationOccurrence> communicationOccurrences = communicationOccurrencesProvider.provideFor(feedbackEnrichment.getCustomerId());
            return new FeedbackDependentEnrichment(feedbackEnrichment.getFeedbacks(), communicationOccurrences);
        } catch (RuntimeException e) {
            log.warn("Exception occurred in feedback enricher 2/2 for customer id {}.", feedbackEnrichment.getCustomerId(), e);
            return new ThrowableEnrichment(e);
        }
    }
}
