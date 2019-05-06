package com.hclc.enrichers.communication.boundary;

import com.hclc.enrichers.communication.CommunicationProperties;
import com.hclc.enrichers.communication.control.CommunicationOccurrencesRepository;
import com.hclc.enrichers.communication.entity.CommunicationOccurrence;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/communicationOccurrences")
public class CommunicationOccurrencesRestController {
    private final CommunicationOccurrencesRepository communicationOccurrencesRepository;
    private final CommunicationProperties communicationProperties;

    public CommunicationOccurrencesRestController(CommunicationOccurrencesRepository communicationOccurrencesRepository, CommunicationProperties communicationProperties) {
        this.communicationOccurrencesRepository = communicationOccurrencesRepository;
        this.communicationProperties = communicationProperties;
    }

    @GetMapping
    ResponseEntity<List<CommunicationOccurrence>> findBy(@RequestParam("ids") Set<String> ids) throws InterruptedException {
        sleep(communicationProperties.getSimulatedProcessingTimeMillis());
        return ResponseEntity.ok(communicationOccurrencesRepository.findByIds(ids));
    }
}
