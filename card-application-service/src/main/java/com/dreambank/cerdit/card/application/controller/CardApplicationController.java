package com.dreambank.cerdit.card.application.controller;


import com.dreambank.cerdit.card.application.data.CardApplicationData;

import com.dreambank.cerdit.card.application.model.CardApplicationRequest;
import com.dreambank.cerdit.card.application.model.CardApplicationResponse;
import com.dreambank.cerdit.card.application.model.DecisionResponse;
import com.dreambank.cerdit.card.application.service.CardApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
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

    @GetMapping("/report/{status}/{applicationDate}")
    public Flux<CardApplicationData> findbyStatusDate(@PathVariable("status") String status, @PathVariable("applicationDate") LocalDate applicationDate) {
        return service.findByStatusAndApplicationDate(status,applicationDate);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "APPROVED / DECLINED /PENDING"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable")})
    @Operation(summary = "Card Application API")
    @PostMapping("/submit")
    public ResponseEntity<Mono<CardApplicationResponse>> save(@RequestBody CardApplicationRequest cardApplicationRequest) throws ExecutionException, InterruptedException {

            Mono<CardApplicationResponse> applicationStatusMono = service.processApplication(cardApplicationRequest);
            return new ResponseEntity<Mono<CardApplicationResponse>>(applicationStatusMono, HttpStatus.OK);

    }
}
