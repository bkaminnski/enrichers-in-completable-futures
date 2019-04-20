package com.hclc.enrichers.feedback.control;

import com.hclc.enrichers.feedback.entity.Feedback;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.hclc.enrichers.feedback.entity.FeedbackCollectionType.*;
import static com.hclc.enrichers.feedback.entity.FeedbackStatus.*;

@Repository
public class FeedbackRepository {
    private final ConcurrentHashMap<String, List<Feedback>> feedbackByCustomerId = new ConcurrentHashMap<>();

    @PostConstruct
    public void prepareData() {
        List<Feedback> feedbacks = new LinkedList<>();
        feedbacks.add(new Feedback("593ac471-6207-4234-8a23-10c0216d04b0", "72b92ae7-f7b6-405e-85d7-7224b114ee70", parse("2000-04-04T13:58:13"), PHONE_AUTOMATED_SURVEY, IGNORED, null));
        feedbacks.add(new Feedback("381ec40e-e0b3-4526-b8cc-bfba88619ef5", "72b92ae7-f7b6-405e-85d7-7224b114ee70", parse("2000-04-05T11:13:23"), PHONE_AUTOMATED_SURVEY, IGNORED, null));
        feedbacks.add(new Feedback("ee693dd3-abb9-4c32-acf2-1d8b766fd2f8", "72b92ae7-f7b6-405e-85d7-7224b114ee70", parse("2000-04-06T15:42:51"), PHONE_AUTOMATED_SURVEY, FINISHED, 3));
        feedbacks.add(new Feedback("55f223f9-f57f-4d17-81cc-84df61eacac3", "b5a88c44-644e-4672-a0c5-0efea6f86243", parse("2000-04-11T12:10:14"), PHONE_AUTOMATED_SURVEY, FINISHED, 4));
        feedbacks.add(new Feedback("44d767e7-3375-4ffa-9f9f-0a78b64b41ee", "", parse("2012-06-01T10:12:31"), PHONE_MANUAL_SURVEY, ABORTED, null));
        feedbackByCustomerId.put("45e091bf-edbf-4f80-9dd4-90ad50fe131a", feedbacks);
        feedbacks = new LinkedList<>();
        feedbacks.add(new Feedback("9c1e1b4c-dfbe-404a-9b6e-8d4a4351d571", "10c45eae-2a2b-41cd-86e3-22b486ba3490", parse("2017-11-25T14:10:18"), PHONE_MANUAL_SURVEY, IGNORED, null));
        feedbacks.add(new Feedback("cb9a5653-f292-4344-827e-9f3674541337", "10c45eae-2a2b-41cd-86e3-22b486ba3490", parse("2017-11-26T15:16:10"), PHONE_MANUAL_SURVEY, IGNORED, null));
        feedbacks.add(new Feedback("50930a92-6250-4563-b2a1-45a8b0a676f1", "10c45eae-2a2b-41cd-86e3-22b486ba3490", parse("2017-11-27T14:05:43"), PHONE_MANUAL_SURVEY, IGNORED, null));
        feedbacks.add(new Feedback("44d767e7-3375-4ffa-9f9f-0a78b64b41ee", "", parse("2012-06-04T01:07:46"), EMAIL_SURVEY, FINISHED, 5));
        feedbacks.add(new Feedback("2a3a3533-b320-4f97-aa3d-73eecafe09ca", "", parse("2017-12-03T15:00:17"), EMAIL_SURVEY, FINISHED, 4));
        feedbackByCustomerId.put("ab7a68cb-bcd2-4580-a155-a2bd005f4fde", feedbacks);
    }

    private Date parse(String date) {
        return Date.from(LocalDateTime.parse(date).atZone(ZoneOffset.systemDefault()).toInstant());
    }

    public List<Feedback> findByCustomerId(String customerId) {
        return Collections.unmodifiableList(feedbackByCustomerId.getOrDefault(customerId, new LinkedList<>()));
    }
}
