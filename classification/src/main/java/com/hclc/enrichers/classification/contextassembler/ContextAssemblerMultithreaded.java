package com.hclc.enrichers.classification.contextassembler;

import com.hclc.enrichers.classification.ClassificationProperties;
import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackDependentEnricher;
import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackEnricher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class ContextAssemblerMultithreaded {
    private final List<Enricher> enrichers;
    private final FeedbackEnricher feedbackEnricher;
    private final FeedbackDependentEnricher feedbackDependentEnricher;
    private final ClassificationProperties classificationProperties;
    private final Executor enrichersThreadPoolExecutor;

    public ContextAssemblerMultithreaded(List<Enricher> enrichers, FeedbackEnricher feedbackEnricher, FeedbackDependentEnricher feedbackDependentEnricher, ClassificationProperties classificationProperties, Executor enrichersThreadPoolExecutor) {
        this.enrichers = enrichers;
        this.feedbackEnricher = feedbackEnricher;
        this.feedbackDependentEnricher = feedbackDependentEnricher;
        this.classificationProperties = classificationProperties;
        this.enrichersThreadPoolExecutor = enrichersThreadPoolExecutor;
    }

    public Context assembleFor(String customerId) {
        Context context = new Context();
        List<CompletableFuture<Enrichment>> enrichmentFutures = runEnrichers(customerId);
        waitUntilEnrichersFinishOrTimeOut(enrichmentFutures, customerId);
        applyEnrichmentsToContext(context, enrichmentFutures, customerId);
        return context;
    }

    private List<CompletableFuture<Enrichment>> runEnrichers(String customerId) {
        return Stream.concat(
                runOneStepEnrichers(customerId),
                runTwoStepsEnrichers(customerId)
        ).collect(toList());
    }

    private Stream<CompletableFuture<Enrichment>> runOneStepEnrichers(String customerId) {
        return enrichers.stream().map(e -> supplyAsync(() -> e.run(customerId), enrichersThreadPoolExecutor));
    }

    private Stream<CompletableFuture<Enrichment>> runTwoStepsEnrichers(String customerId) {
        return Stream.of(supplyAsync(() -> feedbackEnricher.run(customerId), enrichersThreadPoolExecutor).thenApply(feedbackDependentEnricher::run));
    }

    private void waitUntilEnrichersFinishOrTimeOut(List<CompletableFuture<Enrichment>> futures, String customerId) {
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        try {
            all.get(classificationProperties.getAssemblyTimeoutMillis(), MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.warn("One of enrichers finished with exception for customer id {}. Classification context may be incomplete.", customerId, e);
        } catch (TimeoutException e) {
            log.warn("Waiting for all enrichers to finish timed out for customer id {}. Classification context may be incomplete.", customerId, e);
        }
    }

    private void applyEnrichmentsToContext(Context context, List<CompletableFuture<Enrichment>> futures, String customerId) {
        futures.stream()
                .map(f -> toEnrichment(f, customerId))
                .forEach(f -> f.applyTo(context));
    }

    private Enrichment toEnrichment(CompletableFuture<Enrichment> f, String customerId) {
        try {
            return f.getNow(new EnrichmentDoingNothing());
        } catch (CompletionException e) {
            log.warn("One of enrichers finished with exception for customer id {}. Classification context may be incomplete.", customerId, e);
            return new EnrichmentDoingNothing();
        }
    }
}
