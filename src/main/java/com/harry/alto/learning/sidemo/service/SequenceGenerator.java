package com.harry.alto.learning.sidemo.service;

import com.harry.alto.learning.sidemo.service.db.SequenceGeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SequenceGenerator {

    @Autowired
    SequenceGeneratorRepository sequenceGeneratorRepository;
    private Long sequence;

    /**
     * This method is actually backed by db sequence
     *
     * @return
     */
    public Mono<Long> getSequence() {
        return Mono.create(longMonoSink -> {
            synchronized (sequence) {
                sequence = sequenceGeneratorRepository.getSequence().block();
                longMonoSink.success(sequence);
            }
        });
    }

    public Mono<List<Long>> getSequenceInBulk() {
        return sequenceGeneratorRepository.getSequenceInBulk();
    }
}
