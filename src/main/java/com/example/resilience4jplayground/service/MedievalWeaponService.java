package com.example.resilience4jplayground.service;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MedievalWeaponService {

    private final WebClient webClient;

    public List<String> getWeapons() {

        List<String> availableMaterials = getAvailableMaterials();
        log.info("availableMaterials: [{}]", availableMaterials);
        return checkWeaponsAvailableToCraft(availableMaterials);

    }

    public List<String> checkWeaponsAvailableToCraft(List<String> materials) {
        return Arrays.asList("Wooden Sword", "Diamond Club", "Flame Sword");
    }

    public List<String> getAvailableMaterials() {
        String[] availableMaterials = webClient.get()
                .uri("http://localhost:9998/api/materials")
                .retrieve()
                .bodyToMono(String[].class).block();

        if ( availableMaterials == null ) {
            throw new RuntimeException("Error to fetch materials");
        }

        return Arrays.asList(availableMaterials);
    }







}
