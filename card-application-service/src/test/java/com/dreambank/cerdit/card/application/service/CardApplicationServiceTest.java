package com.dreambank.cerdit.card.application.service;

import com.dreambank.cerdit.card.application.data.CardApplicationData;
import com.dreambank.cerdit.card.application.model.*;
import com.dreambank.cerdit.card.application.repository.CardApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CardApplicationService.class)
class CardApplicationServiceTest {

    @Autowired
    CardApplicationService service;

    @MockBean
    private WebClientService webClientService;

    @MockBean
    private CardApplicationRepository cardApplicationRepository;

    @Test
    void processApplication() {
        Address address = new Address("Test Address", "Test Address1" , "Test", "33344");
        CardApplicationRequest request = CardApplicationRequest.builder()
                        .firstName("John").lastName("Root").ssn("555-55-5555").phone("244-666-5454").birthDate(LocalDate.parse("1980-12-03")).address(address).build();


        DecisionResponse decisionResponse = DecisionResponse.builder().status("Approved").equifaxCreditScore(650).experianCreditScore(655).transUnionCreditScore(649).build();

        CardApplicationData cardApplicationData = CardApplicationData.builder().firstName("John").lastName("Root").build();
        CardApplicationResponse response = mock(CardApplicationResponse.class);
        when(response.getStatus()).thenReturn("Approved");

        when(cardApplicationRepository.save(any(CardApplicationData.class))).thenReturn(Mono.just(cardApplicationData));
        when(webClientService.getCreditScore(any(CreditScoreRequest.class))).thenReturn(Mono.just(decisionResponse));
        Mono<CardApplicationResponse> actual = service.processApplication(request);
        StepVerifier
                .create(actual)
                .expectNextCount(1)
                .expectComplete()
                .verify();

    }

    @Test
    void findAll() {
        CardApplicationData response = mock(CardApplicationData.class);
        when(response.getStatus()).thenReturn("Approved");
        when(cardApplicationRepository.findAll()).thenReturn(Flux.just(response));
        Flux<CardApplicationData> actual =  service.findAll();
        StepVerifier
                .create(actual)
                .expectNext(response)
                .expectComplete()
                .verify();
    }

    @Test
    void findCardApplicationByName() {
        CardApplicationData response = mock(CardApplicationData.class);
        when(response.getStatus()).thenReturn("Approved");
        when(cardApplicationRepository.findByFirstNameAndLastName("Joe", "Root")).thenReturn(Flux.just(response));
        Flux<CardApplicationData> actual =  service.findCardApplicationByName("Joe", "Root");
        StepVerifier
                .create(actual)
                .expectNext(response)
                .expectComplete()
                .verify();
    }

    @Test
    void findCardApplicationByStatusDate() {
        CardApplicationData response = mock(CardApplicationData.class);
        when(response.getStatus()).thenReturn("Approved");
        when(cardApplicationRepository.findByStatusAndApplicationDate("APPROVED", LocalDate.parse("2024-09-02"))).thenReturn(Flux.just(response));
        Flux<CardApplicationData> actual =  service.findByStatusAndApplicationDate("APPROVED", LocalDate.parse("2024-09-02"));
        StepVerifier
                .create(actual)
                .expectNext(response)
                .expectComplete()
                .verify();
    }
}