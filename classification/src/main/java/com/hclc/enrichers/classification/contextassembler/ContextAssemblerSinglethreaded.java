package com.hclc.enrichers.classification.contextassembler;

import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackDependentEnricher;
import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackEnricher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ContextAssemblerSinglethreaded {
    private final List<Enricher> enrichers;
    private final FeedbackEnricher feedbackEnricher;
    private final FeedbackDependentEnricher feedbackDependentEnricher;

    public ContextAssemblerSinglethreaded(List<Enricher> enrichers, FeedbackEnricher feedbackEnricher, FeedbackDependentEnricher feedbackDependentEnricher) {
        this.enrichers = enrichers;
        this.feedbackEnricher = feedbackEnricher;
        this.feedbackDependentEnricher = feedbackDependentEnricher;
    }

    public Context assembleFor(String customerId) {
        Context context = new Context(customerId);
        enrichers.stream()
                .map(enricher -> enricher.run(customerId))
                .forEach(enrichment -> enrichment.applyTo(context));
        feedbackDependentEnricher
                .run(feedbackEnricher.run(customerId))
                .applyTo(context);
        return context;
    }
}
