package com.harry.alto.learning.sidemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalIdHandler {
    @Autowired
    private UniqueNumberGenerator uniqueNumberGenerator;

    public Mono<Long> getId() {
        return uniqueNumberGenerator.getUniqueNumber();
    }

}
