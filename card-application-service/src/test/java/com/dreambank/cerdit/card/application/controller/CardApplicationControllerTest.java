package com.dreambank.cerdit.card.application.controller;

import com.dreambank.cerdit.card.application.data.CardApplicationData;

import com.dreambank.cerdit.card.application.model.CardApplicationRequest;
import com.dreambank.cerdit.card.application.model.CardApplicationResponse;
import com.dreambank.cerdit.card.application.service.CardApplicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CardApplicationController.class)
class CardApplicationControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CardApplicationService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        CardApplicationData response = mock(CardApplicationData.class);
        when(response.getStatus()).thenReturn("Approved");
        when(service.findAll()).thenReturn(Flux.just(response));
        webClient.get().uri("/api/v1/application/").exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void findbyName() {
        CardApplicationData response = mock(CardApplicationData.class);
        when(response.getStatus()).thenReturn("Approved");
        when(service.findCardApplicationByName("Joe", "Root")).thenReturn(Flux.just(response));
        webClient.get().uri("/api/v1/application/Joe/Root").exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void findbyStatusandApplicationDate() {
        CardApplicationData response = mock(CardApplicationData.class);
        when(response.getStatus()).thenReturn("Approved");
        when(service.findByStatusAndApplicationDate("APPROVED", LocalDate.parse("2024-09-02"))).thenReturn(Flux.just(response));
        webClient.get().uri("/api/v1/application/report/APPROVED/2024-09-02").exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }

    @Test
    void save_success() {
        CardApplicationRequest request = mock(CardApplicationRequest.class);
        when(request.getFirstName()).thenReturn("John");
        when(request.getLastName()).thenReturn("Root");
        when(request.getSsn()).thenReturn("555-55-5555");
        CardApplicationResponse response = mock(CardApplicationResponse.class);
        when(response.getStatus()).thenReturn("Approved");

        when(service.processApplication(request)).thenReturn(Mono.just(response));
        webClient.post().uri("/api/v1/application/submit").bodyValue(request).exchange()
                .expectStatus()
                .isOk()
                .expectBody();
                //.jsonPath("status").isEqualTo("Approved");

    }


}