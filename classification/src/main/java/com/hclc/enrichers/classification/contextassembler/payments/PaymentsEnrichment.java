package com.hclc.enrichers.classification.contextassembler.payments;

import com.hclc.enrichers.classification.contextassembler.Context;
import com.hclc.enrichers.classification.contextassembler.Enrichment;
import com.hclc.enrichers.classification.providers.payments.Payment;

import java.util.List;

public class PaymentsEnrichment implements Enrichment {
    private final PaymentsSummary paymentsSummary;

    PaymentsEnrichment(List<Payment> payments) {
        paymentsSummary = PaymentsSummary.builder()
                .numberOfAllPayments(payments.size())
                .numberOfOverduePayments((int) payments.stream().filter(Payment::withOverdue).count())
                .build();
    }

    @Override
    public void applyTo(Context context) {
        context.setPayments(paymentsSummary);
    }
}
