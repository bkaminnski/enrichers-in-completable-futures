package com.hclc.enrichers.classification.contextassembler.feedback;

import com.hclc.enrichers.classification.contextassembler.Context;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrence;
import com.hclc.enrichers.classification.providers.communication.CommunicationOccurrenceType;
import com.hclc.enrichers.classification.providers.feedback.Feedback;
import com.hclc.enrichers.classification.providers.feedback.FeedbackStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static com.hclc.enrichers.classification.contextassembler.feedback.FeedbackType.*;
import static com.hclc.enrichers.classification.providers.communication.CommunicationOccurrenceType.EMAIL;
import static com.hclc.enrichers.classification.providers.communication.CommunicationOccurrenceType.PHONE;
import static com.hclc.enrichers.classification.providers.feedback.FeedbackStatus.*;
import static java.util.stream.Collectors.*;

public class FeedbackDependentEnrichment implements Enrichment {
    private final List<FeedbackSummary> feedbackSummaries;

    FeedbackDependentEnrichment(List<Feedback> feedbacks, List<CommunicationOccurrence> communicationOccurrences) {
        Map<String, CommunicationOccurrence> communicationById = communicationOccurrences.stream().collect(toMap(CommunicationOccurrence::getId, c -> c, (l, r) -> r));
        Map<FeedbackStatus, Long> generalByStatus = feedbacks.stream()
                .filter(generalFeedback())
                .collect(countingByFeedbackStatus());
        Map<CommunicationOccurrenceType, Map<FeedbackStatus, Long>> byCommunicationTypeByStatus = feedbacks.stream()
                .filter(communicationRelatedFeedback(communicationById))
                .collect(groupingByCommunicationTypeCountingByFeedbackStatus(communicationById));
        feedbackSummaries = Stream.of(
                buildFeedbackSummary(GENERAL, generalByStatus),
                buildFeedbackSummary(REGARDING_EMAIL_COMMUNICATION, byCommunicationTypeByStatus.getOrDefault(EMAIL, new HashMap<>())),
                buildFeedbackSummary(REGARDING_PHONE_COMMUNICATION, byCommunicationTypeByStatus.getOrDefault(PHONE, new HashMap<>()))
        ).collect(toList());
    }

    private static Predicate<Feedback> generalFeedback() {
        return f -> f.getCommunicationId() == null || f.getCommunicationId().isBlank();
    }

    private static Collector<Feedback, ?, Map<FeedbackStatus, Long>> countingByFeedbackStatus() {
        return groupingBy(Feedback::getStatus, counting());
    }

    private static Predicate<Feedback> communicationRelatedFeedback(Map<String, CommunicationOccurrence> communicationById) {
        return f -> f.getCommunicationId() != null && communicationById.get(f.getCommunicationId()) != null;
    }

    private static Collector<Feedback, ?, Map<CommunicationOccurrenceType, Map<FeedbackStatus, Long>>> groupingByCommunicationTypeCountingByFeedbackStatus(Map<String, CommunicationOccurrence> communicationById) {
        return groupingBy(f -> communicationById.get(f.getCommunicationId()).getType(), countingByFeedbackStatus());
    }

    private FeedbackSummary buildFeedbackSummary(FeedbackType feedbackType, Map<FeedbackStatus, Long> feedbacksCountByStatus) {
        return FeedbackSummary.builder()
                .feedbackType(feedbackType)
                .numberOfAbortedFeedbacks(feedbacksCountByStatus.getOrDefault(ABORTED, 0L))
                .numberOfIgnoredFeedbacks(feedbacksCountByStatus.getOrDefault(IGNORED, 0L))
                .numberOfFinishedFeedbacks(feedbacksCountByStatus.getOrDefault(FINISHED, 0L))
                .numberOfAllFeedbackRequests(feedbacksCountByStatus.values().stream().mapToLong(Long::longValue).sum())
                .build();
    }

    @Override
    public void applyTo(Context context) {
        context.setFeedback(feedbackSummaries);
    }
}
