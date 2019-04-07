package com.hclc.enrichers.payments.control;

import com.hclc.enrichers.payments.entity.Payment;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PaymentsRepository {
    private final ConcurrentHashMap<String, List<Payment>> paymentsByCustomerId = new ConcurrentHashMap<>();

    @PostConstruct
    public void prepareData() {
        paymentsByCustomerId.put("45e091bf-edbf-4f80-9dd4-90ad50fe131a", generate("86d9863e-9baf-49e8-80e8-0e27ed7c90fc", "2000-04-17T23:59:59", "2019-03-17T23:59:59", new BigDecimal("458.92")));
        paymentsByCustomerId.put("ab7a68cb-bcd2-4580-a155-a2bd005f4fde", generate("0b67c86f-d9cc-41ce-909f-a9bcdf1f6a50", "2017-12-02T23:59:59", "2019-04-02T23:59:59", new BigDecimal("188.19")));
    }

    private List<Payment> generate(String policyId, String firstPaymentDueDate, String lastPaymentDueDate, BigDecimal amount) {
        List<Payment> payments = new LinkedList<>();
        LocalDateTime dueDate = LocalDateTime.parse(firstPaymentDueDate);
        LocalDateTime lastDueDate = LocalDateTime.parse(lastPaymentDueDate);
        int i = 0;
        while (!dueDate.isAfter(lastDueDate)) {
            int daysLate = 0;
            Date paymentDate = asDate(dueDate.minusDays(2).withHour(12).withMinute(0).withSecond(0));
            if (i > 0 && i % 10 == 0) {
                daysLate = 3;
                paymentDate = asDate(dueDate.plusDays(daysLate).withHour(12).withMinute(0).withSecond(0));
            }
            payments.add(new Payment(policyId, asDate(dueDate), paymentDate, amount, daysLate));
            dueDate = dueDate.plusMonths(1);
            i++;
        }
        return payments;
    }

    private Date asDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public List<Payment> findByCustomerId(String customerId) {
        return Collections.unmodifiableList(paymentsByCustomerId.getOrDefault(customerId, new LinkedList<>()));
    }
}
