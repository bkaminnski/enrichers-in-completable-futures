package com.hclc.enrichers.payments.entity;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

@Value
public class Payment {
    private String policyId;
    private Date dueDate;
    private Date paymentDate;
    private BigDecimal amount;
    private int daysLate;
}
