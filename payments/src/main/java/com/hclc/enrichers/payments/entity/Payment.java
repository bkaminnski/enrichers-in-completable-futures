package com.hclc.enrichers.payments.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {
    private String policyId;
    private Date dueDate;
    private Date paymentDate;
    private BigDecimal amount;
    private int daysLate;

    public Payment(String policyId, Date dueDate, Date paymentDate, BigDecimal amount, int daysLate) {
        this.policyId = policyId;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.daysLate = daysLate;
    }

    public String getPolicyId() {
        return policyId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getDaysLate() {
        return daysLate;
    }
}
