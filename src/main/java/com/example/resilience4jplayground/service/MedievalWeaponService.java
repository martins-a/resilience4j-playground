package com.example.resilience4jplayground.service;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MedievalWeaponService {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    public final String WEAPON_SERVICE = "weaponService";
    private final WebClient webClient;



    @CircuitBreaker(name=WEAPON_SERVICE, fallbackMethod = "getBasicWeapons")
    public String getWeapons() {

        Boolean availableMaterials = webClient.get()
                .uri("http://localhost:9998/api/materials")
                .retrieve()
                .bodyToMono(Boolean.class).block();

        //Boolean availableMaterials = getBasicWeapon1();



        return "These are the weapons";
    }


    public Boolean getBasicWeapon1() {
        return restTemplate
                .getForObject("http://localhost:9998/api/materials", Boolean.class);
    }

    public String getBasicWeapons(Exception e) {
        return "this are the default weapons";
    }




}
