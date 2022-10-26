package com.example.resilience4jplayground.controller;

import com.example.resilience4jplayground.service.MedievalWeaponService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/weapons")
@RequiredArgsConstructor
@Log4j2
public class MedievalWeaponController {

    public final String WEAPON_SERVICE = "weaponService";

    @Autowired
    private final MedievalWeaponService medievalWeaponService;

    private int attempt=1;

    /**
     * CIRCUIT BREAKER EXAMPLES
     * 1 - Analyze parameters
     * 3 - Up virtualized service on mountebank
     * 2 - Do calls with remote service working and check health status
     * 4 - Shutdown virtualized materials service
     * 5 - Check statistics on health status and turn on again
     * 6 - Use fallback method
     * 7 - Change configuration for option 2 - TIME BASED
     * RETRY EXAMPLE 1
     * 1 - Do N calls with some falling
     * 2 - Do N calls with retry working
     * 3 - Use a fallback method
     * @return list of weapons
     */
    @GetMapping
    //@CircuitBreaker(name=WEAPON_SERVICE, fallbackMethod = "getBasicWeapons")
    //@CircuitBreaker(name=WEAPON_SERVICE)
    @Retry(name=WEAPON_SERVICE)
    //@Retry(name=WEAPON_SERVICE, fallbackMethod = "getBasicWeapons")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getWeapons() {
        log.info("getWeapons invoked, attempt number {} at {}", attempt++, new Date());
        return medievalWeaponService.getWeapons();
    }

    // fallback method for getWeapons
    public List<String> getBasicWeapons(Exception e) {
        List<String> cachedWeapons = Arrays.asList("Wooden Sword", "Iron Club");
        log.info("fallback method getBasicWeapons called: [{}]", cachedWeapons);
        return cachedWeapons;
    }

    /**
     * BULKHEAD EXAMPLE 1
     * 1. Call multiple times api
     * 2. Continue to call another apis
     * @throws InterruptedException if the execution got interrupted
     */
    @PostMapping("/postOrder")
    public void postWeaponOrder() throws InterruptedException {
        medievalWeaponService.sendCraftOrder();
    }

    /**
     * RATE LIMITER EXAMPLE 1
     */
    @PostMapping("/postOrder/batch")
    public void postWeaponOrderInBatch() {
        medievalWeaponService.sendCraftOrderInBatch();
    }


}
