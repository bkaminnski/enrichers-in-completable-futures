package com.hclc.enrichers.classificationreactive.contextassembler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ContextAssemblerReactive {

    public Mono<Context> assembleFor(String customerId) {
        throw new RuntimeException("Not implemented yet");
    }
}
