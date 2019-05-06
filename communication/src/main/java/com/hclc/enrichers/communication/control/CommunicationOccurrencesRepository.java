package com.hclc.enrichers.communication.control;

import com.hclc.enrichers.communication.entity.CommunicationOccurrence;
import com.hclc.enrichers.communication.entity.CommunicationOccurrenceType;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toUnmodifiableList;

@Repository
public class CommunicationOccurrencesRepository {
    private final ConcurrentHashMap<String, CommunicationOccurrence> communicationOccurrenceById = new ConcurrentHashMap<>();

    @PostConstruct
    public void prepareData() {
        addToMap(new CommunicationOccurrence("72b92ae7-f7b6-405e-85d7-7224b114ee70", parse("2000-04-01T14:12:42"), "", CommunicationOccurrenceType.PHONE));
        addToMap(new CommunicationOccurrence("b5a88c44-644e-4672-a0c5-0efea6f86243", parse("2000-04-07T13:17:57"), "", CommunicationOccurrenceType.EMAIL));
        addToMap(new CommunicationOccurrence("e26fbe50-ed13-48aa-ba2b-84fbf6ab8e9c", parse("2017-09-17T18:09:51"), "", CommunicationOccurrenceType.SMS));
        addToMap(new CommunicationOccurrence("10c45eae-2a2b-41cd-86e3-22b486ba3490", parse("2017-11-23T10:12:45"), "", CommunicationOccurrenceType.PHONE));
    }

    private void addToMap(CommunicationOccurrence communicationOccurrence) {
        communicationOccurrenceById.put(communicationOccurrence.getId(), communicationOccurrence);
    }

    private Date parse(String date) {
        return Date.from(LocalDateTime.parse(date).atZone(ZoneOffset.systemDefault()).toInstant());
    }

    public List<CommunicationOccurrence> findByIds(Set<String> ids) {
        return ids.stream().map(communicationOccurrenceById::get).filter(Objects::nonNull).collect(toUnmodifiableList());
    }
}
