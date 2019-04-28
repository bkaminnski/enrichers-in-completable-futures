package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.contextassembler.Enricher;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrencesProvider;
import org.springframework.stereotype.Component;

@Component
public class CommunicationEnricher {
    private final CommunicationOccurrencesProvider communicationOccurrencesProvider;

    public CommunicationEnricher(CommunicationOccurrencesProvider communicationOccurrencesProvider) {
        this.communicationOccurrencesProvider = communicationOccurrencesProvider;
    }

    public Enricher run(FeedbackEnrichment feedbackEnrichment) {
        return new FeedbackEnrichment();
    }
}
