package com.hclc.enrichers.classification.boundary;

import com.hclc.enrichers.classification.control.ClassificationContextAssembler;
import com.hclc.enrichers.classification.control.ClassificationRunner;
import com.hclc.enrichers.classification.entity.Classification;
import com.hclc.enrichers.classification.entity.ClassificationContext;
import com.hclc.enrichers.classification.entity.ClassificationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classification")
public class ClassificationRestController {
    private final ClassificationContextAssembler assembler;
    private final ClassificationRunner runner;

    public ClassificationRestController(ClassificationContextAssembler assembler, ClassificationRunner runner) {
        this.assembler = assembler;
        this.runner = runner;
    }

    @GetMapping
    ResponseEntity<Classification> getClassification(@RequestParam("customerId") String customerId) {
        ClassificationContext context = assembler.assembleFor(customerId);
        ClassificationResult result = runner.runClassificationFor(context);
        return ResponseEntity.ok(new Classification(customerId, context, result));
    }
}
