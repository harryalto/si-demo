package com.harry.alto.learning.sidemo.config;


import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "globalIdGateway", defaultRequestChannel = "sampleChannel")
public interface GlobalIdGateway {
    void sayHello(String name);
}