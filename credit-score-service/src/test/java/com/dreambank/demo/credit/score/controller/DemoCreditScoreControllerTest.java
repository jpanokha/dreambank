package com.dreambank.demo.credit.score.controller;

import com.dreambank.demo.credit.score.model.CreditScoreRequest;
import com.dreambank.demo.credit.score.model.CreditScoreResponse;
import com.dreambank.demo.credit.score.service.DemoCreditScoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(DemoCreditScoreController.class)
class DemoCreditScoreControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private DemoCreditScoreService demoCreditScoreService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreditScore() {
        CreditScoreRequest request = CreditScoreRequest.builder()
                                        .firstName("Test First Name")
                                        .lastName("Test Last Name")
                                        .ssn("345-47-1145").build();
        Mockito.when(demoCreditScoreService.getCreditScore(Mockito.anyString())).thenReturn(600);
        webClient.post().uri("/api/v1/demo/credit-score").bodyValue(request).exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("creditScore").isEqualTo(600);


    }
}