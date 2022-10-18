package com.example.resilience4jplayground.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        // create a bean of type webclient
        // it will always declare the bean as webclient
        return WebClient.builder().build();
    }

}