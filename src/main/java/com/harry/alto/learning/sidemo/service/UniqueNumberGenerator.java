package com.harry.alto.learning.sidemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class UniqueNumberGenerator {

    @Autowired
    private SequenceGenerator sequenceGenerator;

    Queue<Long> uniqueNumberCache = new ConcurrentLinkedQueue<>();

    public Mono<Long> getUniqueNumber() {

        Long orderId = uniqueNumberCache.poll();

        if (orderId != null) {
            return Mono.just(orderId);
        } else {
            return generateUniqueNumbers();
        }

    }

    /**
     * This is where the problem lies, When the queue is empty in the above method,
     * All thw thread are concurrently trying to get the db sequence and generating the
     * unique numbers and populating the queue,
     * where I want to only one thread to populate the queue and make all other to wait for it
     *
     * @return
     */

    public Mono<Long> generateUniqueNumbers() {
        return sequenceGenerator.getSequenceInBulk()
                .flatMap(nextVal -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "Generated number " + nextVal);
                    var nextOrderId = nextVal.remove(0);
                    uniqueNumberCache.addAll(nextVal);
                    return Mono.just(nextOrderId);
                });
    }
}
