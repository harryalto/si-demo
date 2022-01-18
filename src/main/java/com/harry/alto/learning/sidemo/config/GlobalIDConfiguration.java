package com.harry.alto.learning.sidemo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAutoConfiguration
@IntegrationComponentScan
public class GlobalIDConfiguration {

    @Bean(name = "sampleChannel")
    public ExecutorChannel sampleChannel() {
        return new ExecutorChannel(asyncExecutor());
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("SI-DEMO");
        executor.initialize();
        return executor;
    }

    @Bean
    @ServiceActivator(inputChannel = "sampleChannel")
    public MessageHandler gidGeneratorHandler() {
        MessageHandler messageHandler = new MessageHandler() {

            @Override
            public void handleMessage(org.springframework.messaging.Message<?> message) throws MessagingException {
                var input = message.getPayload();
                System.out.println("new email received" + input);
            }
        };
        return messageHandler;
    }

    @Bean
    public IntegrationFlow lambdaFlow() {
        return IntegrationFlows.from("sampleChannel")
                .handle(gidGeneratorHandler())
                .get();

    }
}
