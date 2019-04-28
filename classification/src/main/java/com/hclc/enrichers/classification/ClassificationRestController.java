package com.hclc.enrichers.classification;

import com.hclc.enrichers.classification.classificationrunner.ClassificationRunner;
import com.hclc.enrichers.classification.classificationrunner.Result;
import com.hclc.enrichers.classification.contextassembler.Context;
import com.hclc.enrichers.classification.contextassembler.ContextAssemblerMultithreaded;
import com.hclc.enrichers.classification.contextassembler.ContextAssemblerSinglethreaded;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("/classification")
public class ClassificationRestController {
    private final ContextAssemblerSinglethreaded contextAssemblerSinglethreaded;
    private final ContextAssemblerMultithreaded contextAssemblerMultithreaded;
    private final ClassificationRunner classificationRunner;

    public ClassificationRestController(ContextAssemblerSinglethreaded contextAssemblerSinglethreaded, ContextAssemblerMultithreaded contextAssemblerMultithreaded, ClassificationRunner classificationRunner) {
        this.contextAssemblerSinglethreaded = contextAssemblerSinglethreaded;
        this.contextAssemblerMultithreaded = contextAssemblerMultithreaded;
        this.classificationRunner = classificationRunner;
    }

    @GetMapping("/singlethreaded")
    ResponseEntity<Classification> getClassificationSinglethreaded(@RequestParam("customerId") String customerId) {
        return getClassification(customerId, contextAssemblerSinglethreaded::assembleFor);
    }

    @GetMapping("/multithreaded")
    ResponseEntity<Classification> getClassificationMultithreaded(@RequestParam("customerId") String customerId) {
        return getClassification(customerId, contextAssemblerMultithreaded::assembleFor);
    }

    private ResponseEntity<Classification> getClassification(String customerId, Function<String, Context> assemblingFunction) {
        Context context = assemblingFunction.apply(customerId);
        Result result = classificationRunner.runClassificationFor(context);
        return ResponseEntity.ok(new Classification(context, result));
    }
}
