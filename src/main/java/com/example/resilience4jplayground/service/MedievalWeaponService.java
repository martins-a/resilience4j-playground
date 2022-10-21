package com.example.resilience4jplayground.service;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
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

    public final String WEAPON_SERVICE = "weaponService";

    private final WebClient webClient;

    public List<String> getWeapons() {

        List<String> availableMaterials = getAvailableMaterials();
        log.info("availableMaterials: [{}]", availableMaterials);
        return checkWeaponsAvailableToCraft(availableMaterials);

    }

    @Bulkhead(name = WEAPON_SERVICE, fallbackMethod = "bulkHeadFallback")
    public void sendCraftOrder() throws InterruptedException {
        webClient.post()
                .uri("http://localhost:9997/api/craft")
                .retrieve()
                .bodyToMono(String[].class).block();
        log.info("Sent craft order");
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

    public void bulkHeadFallback(Throwable t) {
        log.error("Inside bulkHeadFallback, cause - {}", t.toString());
    }







}
