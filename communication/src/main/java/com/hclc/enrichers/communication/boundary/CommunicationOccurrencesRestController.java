package com.hclc.enrichers.communication.boundary;

import com.hclc.enrichers.communication.CommunicationConfig;
import com.hclc.enrichers.communication.control.CommunicationOccurrencesRepository;
import com.hclc.enrichers.communication.entity.CommunicationOccurrence;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/communicationOccurrences")
public class CommunicationOccurrencesRestController {
    private final CommunicationOccurrencesRepository communicationOccurrencesRepository;
    private final CommunicationConfig communicationConfig;

    public CommunicationOccurrencesRestController(CommunicationOccurrencesRepository communicationOccurrencesRepository, CommunicationConfig communicationConfig) {
        this.communicationOccurrencesRepository = communicationOccurrencesRepository;
        this.communicationConfig = communicationConfig;
    }

    @GetMapping
    ResponseEntity<List<CommunicationOccurrence>> findBy(@RequestParam("customerId") String customerId) throws InterruptedException {
        sleep(communicationConfig.getSimulatedProcessingTimeMillis());
        return ResponseEntity.ok(communicationOccurrencesRepository.findByCustomerId(customerId));
    }
}
