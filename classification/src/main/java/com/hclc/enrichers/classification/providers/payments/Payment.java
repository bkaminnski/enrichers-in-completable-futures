package com.hclc.enrichers.classification.providers.payments;

import lombok.Data;

@Data
public class Payment {
    private int daysLate;

    public boolean withOverdue() {
        return daysLate > 0;
    }
}
