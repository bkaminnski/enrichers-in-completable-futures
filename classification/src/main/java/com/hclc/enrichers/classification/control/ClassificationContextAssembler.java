package com.hclc.enrichers.classification.control;

import com.hclc.enrichers.classification.entity.ClassificationContext;
import org.springframework.stereotype.Component;

@Component
public class ClassificationContextAssembler {

    public ClassificationContext assembleFor(String customerId) {
        return ClassificationContext.builder().build();
    }
}
