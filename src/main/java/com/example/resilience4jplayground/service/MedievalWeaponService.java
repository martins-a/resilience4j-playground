package com.example.resilience4jplayground.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MedievalWeaponService {

    public final String WEAPON_SERVICE = "weaponService";
    private final WebClient webClient;


    @CircuitBreaker(name=WEAPON_SERVICE, fallbackMethod = "getBasicWeapons")
    public String getWeapons() {

        Boolean availableMaterials = webClient.get()
                .uri("http://localhost:9998/api/materials")
                .retrieve()
                .bodyToMono(Boolean.class).block();


        return "These are the weapons";
    }

    public String getBasicWeapons(Exception e) {
        return "These are the basic weapons";
    }



}
