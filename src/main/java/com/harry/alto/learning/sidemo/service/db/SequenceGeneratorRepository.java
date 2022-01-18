package com.harry.alto.learning.sidemo.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RequiredArgsConstructor
@Component
@Slf4j
public class SequenceGeneratorRepository {

    private final DatabaseClient databaseClient;

    public Mono<List<Long>> getSequenceInBulk() {
        return getSequence().map(value -> {
                    List<Long> results = LongStream.rangeClosed(1, 9)
                            .boxed().map(input ->
                                    input + value)
                            .collect(Collectors.toList());
                    results.add(0, value);
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "Result " + results);
                    return results;
                }
        );
    }

    public Mono<Long> getSequence() {
        return this.databaseClient
                .sql("SELECT nextval('gid_sequence_new') as cnt")
                .map(row -> {
                    Long cnt = row.get("cnt", Long.class);
                    return cnt;
                }).one();
    }
}
