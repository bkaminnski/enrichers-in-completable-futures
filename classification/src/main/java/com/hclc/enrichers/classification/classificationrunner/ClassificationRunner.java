package com.hclc.enrichers.classification.classificationrunner;

import com.hclc.enrichers.classification.contextassembler.Context;
import org.springframework.stereotype.Component;

@Component
public class ClassificationRunner {

    public Result runClassificationFor(Context context) {
        return new Result("Out of scope of this exercise.");
    }
}
