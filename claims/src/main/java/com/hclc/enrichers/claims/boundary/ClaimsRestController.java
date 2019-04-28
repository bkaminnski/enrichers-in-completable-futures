package com.hclc.enrichers.claims.boundary;

import com.hclc.enrichers.claims.ClaimsConfig;
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
    private final ClaimsConfig claimsConfig;

    public ClaimsRestController(ClaimsRepository claimsRepository, ClaimsConfig claimsConfig) {
        this.claimsRepository = claimsRepository;
        this.claimsConfig = claimsConfig;
    }

    @GetMapping
    ResponseEntity<List<Claim>> findBy(@RequestParam("customerId") String customerId) throws InterruptedException {
        sleep(claimsConfig.getSimulatedProcessingTimeMillis());
        return ResponseEntity.ok(claimsRepository.findByCustomerId(customerId));
    }
}
