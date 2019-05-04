package com.hclc.enrichers.classification.contextassembler;

import com.hclc.enrichers.classification.config.ClassificationProperties;
import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackDependentEnricher;
import com.hclc.enrichers.classification.contextassembler.feedback.FeedbackEnricher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
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
    private final Executor enrichersExecutor;

    public ContextAssemblerMultithreaded(List<Enricher> enrichers, FeedbackEnricher feedbackEnricher, FeedbackDependentEnricher feedbackDependentEnricher, ClassificationProperties classificationProperties, Executor enrichersExecutor) {
        this.enrichers = enrichers;
        this.feedbackEnricher = feedbackEnricher;
        this.feedbackDependentEnricher = feedbackDependentEnricher;
        this.classificationProperties = classificationProperties;
        this.enrichersExecutor = enrichersExecutor;
    }

    public Context assembleFor(String customerId) {
        Context context = new Context(customerId);
        List<CompletableFuture<Enrichment>> enrichmentFutures = runEnrichers(customerId);
        List<Enrichment> errorsEnrichments = waitUntilEnrichersFinishOrTimeOut(enrichmentFutures, customerId);
        applyEnrichmentsToContext(context, enrichmentFutures, errorsEnrichments);
        return context;
    }

    private List<CompletableFuture<Enrichment>> runEnrichers(String customerId) {
        return Stream.concat(
                runOneStepEnrichers(customerId),
                runTwoStepsEnrichers(customerId)
        ).collect(toList());
    }

    private Stream<CompletableFuture<Enrichment>> runOneStepEnrichers(String customerId) {
        return enrichers.stream().map(e -> supplyAsync(() -> e.run(customerId), enrichersExecutor));
    }

    private Stream<CompletableFuture<Enrichment>> runTwoStepsEnrichers(String customerId) {
        return Stream.of(supplyAsync(() -> feedbackEnricher.run(customerId), enrichersExecutor).thenApply(feedbackDependentEnricher::run));
    }

    private List<Enrichment> waitUntilEnrichersFinishOrTimeOut(List<CompletableFuture<Enrichment>> futures, String customerId) {
        List<Enrichment> errorsEnrichments = new LinkedList<>();
        CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        try {
            all.get(classificationProperties.getAssemblyTimeoutMillis(), MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.warn("One of enrichers finished with exception for customer id {}. Classification context may be incomplete.", customerId, e);
            errorsEnrichments.add(new ThrowableEnrichment(e));
        } catch (TimeoutException e) {
            log.warn("Waiting for all enrichers to finish timed out for customer id {}. Classification context may be incomplete.", customerId, e);
            errorsEnrichments.add(new ThrowableEnrichment(e));
        }
        return errorsEnrichments;
    }

    private void applyEnrichmentsToContext(Context context, List<CompletableFuture<Enrichment>> futures, List<Enrichment> errorsEnrichments) {
        futures.stream()
                .map(f -> toEnrichment(f, context.getCustomerId()))
                .forEach(f -> f.applyTo(context));
        errorsEnrichments.forEach(f -> f.applyTo(context));
    }

    private Enrichment toEnrichment(CompletableFuture<Enrichment> f, String customerId) {
        try {
            return f.getNow(new EnrichmentDoingNothing());
        } catch (CompletionException e) {
            log.warn("One of enrichers finished with exception for customer id {}. Classification context may be incomplete.", customerId, e);
            return new ThrowableEnrichment(e);
        }
    }
}
