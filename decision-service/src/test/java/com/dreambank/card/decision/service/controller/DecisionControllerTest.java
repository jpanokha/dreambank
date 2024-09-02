package com.dreambank.card.decision.service.controller;

import com.dreambank.card.decision.service.delegate.ClientScoreApiDelegate;
import com.dreambank.card.decision.service.model.CreditScoreRequest;
import com.dreambank.card.decision.service.model.DecisionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(DecisionController.class)
class DecisionControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ClientScoreApiDelegate clientScoreApiDelegate;


    @Test
    void inquireCreditApproval() {
        CreditScoreRequest request = CreditScoreRequest.builder()
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .ssn("345-47-1145").build();

        DecisionResponse decisionResponse = DecisionResponse.builder().status("Approved").equifaxCreditScore(650).experianCreditScore(655).transUnionCreditScore(649).build();
        Mockito.when(clientScoreApiDelegate.getDecisionResponse(Mockito.any(CreditScoreRequest.class))).thenReturn(Mono.just(decisionResponse));
        webClient.post().uri("/api/v1/decision/inquireCreditApproval").bodyValue(request).exchange()
                .expectStatus()
                .isOk()
                .expectBody();

    }
}