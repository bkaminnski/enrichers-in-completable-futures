package com.hclc.enrichers.feedback.boundary;

import com.hclc.enrichers.feedback.FeedbackConfig;
import com.hclc.enrichers.feedback.control.FeedbackRepository;
import com.hclc.enrichers.feedback.entity.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/feedback")
public class FeedbackRestController {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackConfig feedbackConfig;

    public FeedbackRestController(FeedbackRepository feedbackRepository, FeedbackConfig feedbackConfig) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackConfig = feedbackConfig;
    }

    @GetMapping
    ResponseEntity<List<Feedback>> findBy(@RequestParam("customerId") String customerId) throws InterruptedException {
        sleep(feedbackConfig.getSimulatedProcessingTimeMillis());
        return ResponseEntity.ok(feedbackRepository.findByCustomerId(customerId));
    }
}
