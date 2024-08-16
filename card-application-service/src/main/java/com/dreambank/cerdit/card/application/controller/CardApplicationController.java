package com.dreambank.cerdit.card.application.controller;


import com.dreambank.cerdit.card.application.constant.ApplicationStatus;
import com.dreambank.cerdit.card.application.data.CardApplicationData;
import com.dreambank.cerdit.card.application.service.CardApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/application")
@RequiredArgsConstructor
@Slf4j
public class CardApplicationController {
    @Autowired
    private CardApplicationService service;

    @GetMapping("/")
    public Flux<CardApplicationData> findAll() {
        Flux<CardApplicationData> cardApplicationDataFlux = service.findAll();
        return cardApplicationDataFlux;
    }

    @GetMapping("/{firstName}/{lastName}")
    public Flux<CardApplicationData> findbyName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return service.findCardApplicationByName(firstName,lastName);
    }

    @PostMapping("/submit")
    public ResponseEntity<Mono<ApplicationStatus>> save(@RequestBody CardApplicationData cardApplicationData) throws ExecutionException, InterruptedException {
        Mono<ApplicationStatus> applicationStatusMono = service.processApplication(cardApplicationData);
        return new ResponseEntity<Mono<ApplicationStatus>>(applicationStatusMono, HttpStatus.OK);
    }
}
