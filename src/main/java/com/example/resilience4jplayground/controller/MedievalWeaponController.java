package com.example.resilience4jplayground.controller;

import com.example.resilience4jplayground.service.MedievalWeaponService;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weapons")
@RequiredArgsConstructor
public class MedievalWeaponController {

    @Autowired
    private final MedievalWeaponService medievalWeaponService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getWeapons() {
        return medievalWeaponService.getWeapons();
    }



}
