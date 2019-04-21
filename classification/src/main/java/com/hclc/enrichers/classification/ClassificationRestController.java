package com.hclc.enrichers.classification;

import com.hclc.enrichers.classification.contextassembler.ContextAssembler;
import com.hclc.enrichers.classification.contextassembler.Context;
import com.hclc.enrichers.classification.classificationrunner.Result;
import com.hclc.enrichers.classification.classificationrunner.ClassificationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classification")
public class ClassificationRestController {
    private final ContextAssembler contextAssembler;
    private final ClassificationRunner classificationRunner;

    public ClassificationRestController(ContextAssembler contextAssembler, ClassificationRunner classificationRunner) {
        this.contextAssembler = contextAssembler;
        this.classificationRunner = classificationRunner;
    }

    @GetMapping
    ResponseEntity<Classification> getClassification(@RequestParam("customerId") String customerId) {
        Context context = contextAssembler.assembleFor(customerId);
        Result result = classificationRunner.runClassificationFor(context);
        return ResponseEntity.ok(new Classification(customerId, context, result));
    }
}
