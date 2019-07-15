package com.hclc.enrichers.classificationreactive.contextassembler;

import com.hclc.enrichers.classificationreactive.contextassembler.claims.ClaimsSummary;
import com.hclc.enrichers.classificationreactive.contextassembler.payments.PaymentsSummary;
import com.hclc.enrichers.classificationreactive.providers.claims.Claim;
import com.hclc.enrichers.classificationreactive.providers.claims.ClaimsProvider;
import com.hclc.enrichers.classificationreactive.providers.payments.Payment;
import com.hclc.enrichers.classificationreactive.providers.payments.PaymentsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

@Slf4j
@Component
public class ContextAssemblerReactiveSinglethreaded {
    private final ClaimsProvider claimsProvider;
    private final PaymentsProvider paymentsProvider;

    public ContextAssemblerReactiveSinglethreaded(ClaimsProvider claimsProvider, PaymentsProvider paymentsProvider) {
        this.claimsProvider = claimsProvider;
        this.paymentsProvider = paymentsProvider;
    }

    public Mono<Context> assembleFor(String customerId) {
        log.info("Assembling in reactive way in thread {}", Thread.currentThread().getId());

        return Mono.zip(
                claims(customerId),
                payments(customerId)
        )
                .doOnNext(objects -> log.info("Merging in {}", Thread.currentThread().getId()))
                .map(objects -> new Context(customerId, objects.getT1(), objects.getT2()));
    }

    private Mono<ClaimsSummary> claims(String customerId) {
        return claimsProvider.provideObservableFor(customerId)
                .doOnNext(claims -> log.info("Mapping claims in thread {}", Thread.currentThread().getId()))
                .map(claims ->
                        ClaimsSummary.builder()
                                .numberOfAllClaims(claims.size())
                                .numberOfAcceptedClaims((int) claims.stream().filter(Claim::isAccepted).count())
                                .numberOfRejectedClaims((int) claims.stream().filter(Claim::isRejected).count())
                                .build()
                );
    }

    private Mono<PaymentsSummary> payments(String customerId) {
        return paymentsProvider.provideObservableFor(customerId)
                .doOnNext(payments -> log.info("Mapping payments in thread {}", Thread.currentThread().getId()))
                .map(payments ->
                        PaymentsSummary.builder()
                                .numberOfAllPayments(payments.size())
                                .numberOfOverduePayments((int) payments.stream().filter(Payment::withOverdue).count())
                                .build()
                );
    }
}
