package com.hclc.enrichers.communication.control;

import com.hclc.enrichers.communication.entity.CommunicationOccurrence;
import com.hclc.enrichers.communication.entity.CommunicationOccurrenceType;
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
public class CommunicationOccurrencesRepository {
    private final ConcurrentHashMap<String, List<CommunicationOccurrence>> communicationOccurrencesByCustomerId = new ConcurrentHashMap<>();

    @PostConstruct
    public void prepareData() {
        List<CommunicationOccurrence> communicationOccurrences = new LinkedList<>();
        communicationOccurrences.add(new CommunicationOccurrence("72b92ae7-f7b6-405e-85d7-7224b114ee70", parse("2000-04-01T14:12:42"), "", CommunicationOccurrenceType.PHONE));
        communicationOccurrences.add(new CommunicationOccurrence("b5a88c44-644e-4672-a0c5-0efea6f86243", parse("2000-04-07T13:17:57"), "", CommunicationOccurrenceType.EMAIL));
        communicationOccurrencesByCustomerId.put("45e091bf-edbf-4f80-9dd4-90ad50fe131a", communicationOccurrences);
        communicationOccurrences = new LinkedList<>();
        communicationOccurrences.add(new CommunicationOccurrence("e26fbe50-ed13-48aa-ba2b-84fbf6ab8e9c", parse("2017-11-23T10:12:45"), "", CommunicationOccurrenceType.SMS));
        communicationOccurrences.add(new CommunicationOccurrence("10c45eae-2a2b-41cd-86e3-22b486ba3490", parse("2017-11-26T09:14:12"), "", CommunicationOccurrenceType.PHONE));
        communicationOccurrencesByCustomerId.put("ab7a68cb-bcd2-4580-a155-a2bd005f4fde", communicationOccurrences);
    }

    private Date parse(String date) {
        return Date.from(LocalDateTime.parse(date).atZone(ZoneOffset.systemDefault()).toInstant());
    }

    public List<CommunicationOccurrence> findByCustomerId(String customerId) {
        return Collections.unmodifiableList(communicationOccurrencesByCustomerId.getOrDefault(customerId, new LinkedList<>()));
    }
}
