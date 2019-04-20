package com.hclc.enrichers.classification.control;

import com.hclc.enrichers.classification.entity.ClassificationContext;
import com.hclc.enrichers.classification.entity.ClassificationResult;
import org.springframework.stereotype.Component;

@Component
public class ClassificationRunner {

    public ClassificationResult runClassificationFor(ClassificationContext classificationContext) {
        return new ClassificationResult("Out of scope of this exercise.");
    }
}
