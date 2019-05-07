package com.hclc.enrichers.feedback.boundary;

import com.hclc.enrichers.feedback.FeedbackProperties;
import com.hclc.enrichers.feedback.control.FeedbackRepository;
import com.hclc.enrichers.feedback.entity.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/feedback")
public class FeedbackRestController {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackProperties feedbackProperties;

    public FeedbackRestController(FeedbackRepository feedbackRepository, FeedbackProperties feedbackProperties) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackProperties = feedbackProperties;
    }

    @GetMapping
    ResponseEntity<List<Feedback>> findBy(@RequestParam("customerId") String customerId) throws InterruptedException {
        sleep((long) (feedbackProperties.getSimulatedProcessingTimeMillis() * ThreadLocalRandom.current().nextDouble()));
        return ResponseEntity.ok(feedbackRepository.findByCustomerId(customerId));
    }
}
