package com.hclc.enrichers.communication.boundary;

import com.hclc.enrichers.communication.control.CommunicationOccurrencesRepository;
import com.hclc.enrichers.communication.entity.CommunicationOccurrence;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/communicationOccurrences")
public class CommunicationOccurrencesRestController {

    private final CommunicationOccurrencesRepository communicationOccurrencesRepository;

    public CommunicationOccurrencesRestController(CommunicationOccurrencesRepository communicationOccurrencesRepository) {
        this.communicationOccurrencesRepository = communicationOccurrencesRepository;
    }

    @GetMapping
    ResponseEntity<List<CommunicationOccurrence>> findBy(@RequestParam("customerId") String customerId) {
        return ResponseEntity.ok(communicationOccurrencesRepository.findByCustomerId(customerId));
    }
}
