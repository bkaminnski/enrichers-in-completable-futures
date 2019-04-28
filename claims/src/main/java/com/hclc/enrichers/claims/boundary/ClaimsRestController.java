package com.hclc.enrichers.claims.boundary;

import com.hclc.enrichers.claims.ClaimsProperties;
import com.hclc.enrichers.claims.control.ClaimsRepository;
import com.hclc.enrichers.claims.entity.Claim;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/claims")
public class ClaimsRestController {
    private final ClaimsRepository claimsRepository;
    private final ClaimsProperties claimsProperties;

    public ClaimsRestController(ClaimsRepository claimsRepository, ClaimsProperties claimsProperties) {
        this.claimsRepository = claimsRepository;
        this.claimsProperties = claimsProperties;
    }

    @GetMapping
    ResponseEntity<List<Claim>> findBy(@RequestParam("customerId") String customerId) throws InterruptedException {
        sleep(claimsProperties.getSimulatedProcessingTimeMillis());
        return ResponseEntity.ok(claimsRepository.findByCustomerId(customerId));
    }
}
