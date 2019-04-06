package com.hclc.enrichers.claims.boundary;

import com.hclc.enrichers.claims.control.ClaimsRepository;
import com.hclc.enrichers.claims.entity.Claim;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimsRestController {

    private final ClaimsRepository claimsRepository;

    public ClaimsRestController(ClaimsRepository claimsRepository) {
        this.claimsRepository = claimsRepository;
    }

    @GetMapping
    ResponseEntity<List<Claim>> findBy(@RequestParam("customerId") String customerId) {
        return ResponseEntity.ok(claimsRepository.findByCustomerId(customerId));
    }
}
