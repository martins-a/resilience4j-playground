package com.example.resilience4jplayground.controller;

import com.example.resilience4jplayground.service.MedievalWeaponService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    //@CircuitBreaker(name=WEAPON_SERVICE, fallbackMethod = "getBasicWeapons")
    @Retry(name=WEAPON_SERVICE)
    @ResponseStatus(HttpStatus.OK)
    public List<String> getWeapons() {
        log.info("retry method called "+attempt++ +" times "+" at "+new Date());
        return medievalWeaponService.getWeapons();
    }

    public List<String> getBasicWeapons(Exception e) {
        return Arrays.asList("Wooden Sword", "Iron Club");
    }



}
