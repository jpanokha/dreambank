package com.dreambank.cerdit.card.application.service;

import com.dreambank.cerdit.card.application.model.CreditScoreRequest;
import com.dreambank.cerdit.card.application.model.DecisionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.ConnectException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebClientServiceTest {


    private WebClientService webClientService;

    @Test
    void getCreditScore() {
       ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
        WebClient webClient = WebClient.builder().exchangeFunction(exchangeFunction).build();
        webClientService = new WebClientService(webClient);
        CreditScoreRequest request = CreditScoreRequest.builder()
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .ssn("345-47-1145").build();
        String json = "{\n" +
                "    \"status\": \"APPROVED\",\n" +
                "    \"equifaxCreditScore\": 753,\n" +
                "    \"experianCreditScore\": 731,\n" +
                "    \"transUnionCreditScore\": 745\n" +
                "}";
        ClientResponse response = ClientResponse.create(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(json).build();

        when(exchangeFunction.exchange(any(ClientRequest.class))).thenReturn(Mono.just(response));


        Mono<DecisionResponse> responseMono = webClientService.getCreditScore(request);
        StepVerifier
                .create(responseMono)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }



}