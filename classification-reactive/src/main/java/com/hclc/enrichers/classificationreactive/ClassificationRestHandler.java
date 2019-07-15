package com.hclc.enrichers.classificationreactive;

import com.hclc.enrichers.classificationreactive.contextassembler.Context;
import com.hclc.enrichers.classificationreactive.contextassembler.ContextAssemblerReactive;
import com.hclc.enrichers.classificationreactive.contextassembler.ContextAssemblerReactiveSinglethreaded;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ClassificationRestHandler {
    private final ContextAssemblerReactive contextAssemblerReactive;
    private final ContextAssemblerReactiveSinglethreaded contextAssemblerReactiveSinglethreaded;

    public ClassificationRestHandler(ContextAssemblerReactive contextAssemblerReactive,
                                     ContextAssemblerReactiveSinglethreaded contextAssemblerReactiveSinglethreaded) {
        this.contextAssemblerReactive = contextAssemblerReactive;
        this.contextAssemblerReactiveSinglethreaded = contextAssemblerReactiveSinglethreaded;
    }

    public Mono<ServerResponse> getClassificationReactive(ServerRequest request) {
        return request.queryParam("customerId")
                .map(customerId -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(contextAssemblerReactive.assembleFor(customerId), Context.class))
                .orElse(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> getClassificationReactiveInOneThread(ServerRequest request) {
        return request.queryParam("customerId")
                .map(customerId -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(contextAssemblerReactiveSinglethreaded.assembleFor(customerId), Context.class))
                .orElse(ServerResponse.badRequest().build());
    }
}
