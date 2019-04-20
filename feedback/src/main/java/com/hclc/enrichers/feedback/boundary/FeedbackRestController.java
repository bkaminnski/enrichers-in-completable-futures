package com.hclc.enrichers.feedback.boundary;

import com.hclc.enrichers.feedback.control.FeedbackRepository;
import com.hclc.enrichers.feedback.entity.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackRestController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackRestController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    ResponseEntity<List<Feedback>> findBy(@RequestParam("customerId") String customerId) {
        return ResponseEntity.ok(feedbackRepository.findByCustomerId(customerId));
    }
}
