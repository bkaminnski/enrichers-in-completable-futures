package com.hclc.enrichers.classification;

import com.hclc.enrichers.classification.classificationrunner.Result;
import com.hclc.enrichers.classification.contextassembler.Context;
import lombok.Value;

@Value
public class Classification {
    private String customerId;
    private Context context;
    private Result result;
}
