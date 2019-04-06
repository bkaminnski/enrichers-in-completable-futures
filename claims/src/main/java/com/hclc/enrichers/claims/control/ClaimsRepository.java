package com.hclc.enrichers.claims.control;

import com.hclc.enrichers.claims.entity.Claim;
import com.hclc.enrichers.claims.entity.ClaimState;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ClaimsRepository {
    private final ConcurrentHashMap<String, List<Claim>> claimsByCustomerId = new ConcurrentHashMap<>();

    @PostConstruct
    public void prepareData() {
        List<Claim> claims = new LinkedList<>();
        claims.add(new Claim("86d9863e-9baf-49e8-80e8-0e27ed7c90fc", parse("2019-03-03T14:02:34"), ClaimState.REQUESTED, ""));
        claims.add(new Claim("86d9863e-9baf-49e8-80e8-0e27ed7c90fc", parse("2014-09-21T16:13:03"), ClaimState.ACCEPTED, ""));
        claims.add(new Claim("86d9863e-9baf-49e8-80e8-0e27ed7c90fc", parse("2001-02-15T10:01:47"), ClaimState.REJECTED, "Policy terms violated."));
        claimsByCustomerId.put("45e091bf-edbf-4f80-9dd4-90ad50fe131a", claims);
        claims = new LinkedList<>();
        claims.add(new Claim("0b67c86f-d9cc-41ce-909f-a9bcdf1f6a50", parse("2019-04-06T13:13:03"), ClaimState.VALIDATED, "Waiting for documentation."));
        claimsByCustomerId.put("ab7a68cb-bcd2-4580-a155-a2bd005f4fde", claims);
    }

    private Date parse(String date) {
        return Date.from(LocalDateTime.parse(date).atZone(ZoneOffset.systemDefault()).toInstant());
    }

    public List<Claim> findByCustomerId(String customerId) {
        return Collections.unmodifiableList(claimsByCustomerId.getOrDefault(customerId, new LinkedList<>()));
    }
}
