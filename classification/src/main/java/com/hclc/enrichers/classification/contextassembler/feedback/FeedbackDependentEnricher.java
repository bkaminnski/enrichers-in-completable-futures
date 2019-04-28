package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrence;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrencesProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedbackDependentEnricher {
    private final CommunicationOccurrencesProvider communicationOccurrencesProvider;

    public FeedbackDependentEnricher(CommunicationOccurrencesProvider communicationOccurrencesProvider) {
        this.communicationOccurrencesProvider = communicationOccurrencesProvider;
    }

    public Enrichment run(FeedbackEnrichment feedbackEnrichment) {
        List<CommunicationOccurrence> communicationOccurrences = communicationOccurrencesProvider.provideFor(feedbackEnrichment.getCustomerId());
        return new FeedbackDependentEnrichment(feedbackEnrichment.getFeedbacks(), communicationOccurrences);
    }
}
