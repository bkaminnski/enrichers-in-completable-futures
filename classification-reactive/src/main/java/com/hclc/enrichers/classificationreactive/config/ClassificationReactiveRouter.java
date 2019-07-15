package com.hclc.enrichers.classificationreactive.config;

import com.hclc.enrichers.classificationreactive.ClassificationRestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ClassificationReactiveRouter {

    @Bean
    public RouterFunction<ServerResponse> routeReactive(ClassificationRestHandler classificationHandler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/classification/reactive")
                        .and(RequestPredicates.accept(MediaType.ALL)), classificationHandler::getClassificationReactive);
    }

    @Bean
    public RouterFunction<ServerResponse> routeReactiveSinglethreaded(ClassificationRestHandler classificationHandler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/classification/reactive-singlethreaded")
                        .and(RequestPredicates.accept(MediaType.ALL)), classificationHandler::getClassificationReactiveInOneThread);
    }
}
