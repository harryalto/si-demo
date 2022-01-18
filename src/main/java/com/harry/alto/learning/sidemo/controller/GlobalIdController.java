package com.harry.alto.learning.sidemo.controller;

import com.harry.alto.learning.sidemo.config.GlobalIdGateway;
import com.harry.alto.learning.sidemo.service.GlobalIdHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class GlobalIdController {
    public static final String GLOBAL_ID_ENDPOINT_V1 = "/v1/global-ids";

    @Autowired
    private GlobalIdGateway globalIdGateway;
    @Autowired
    private GlobalIdHandler globalIdHandler;

    @PostMapping(GLOBAL_ID_ENDPOINT_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Long> getGlobalId() {
        return globalIdHandler.getId()
                .doOnNext(value -> globalIdGateway.sayHello(value.toString()));

    }
}