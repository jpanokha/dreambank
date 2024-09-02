package com.dreambank.card.decision.service.delegate;

import com.dreambank.card.decision.service.client.CardDecisionHelper;
import com.dreambank.card.decision.service.client.EquifaxApiWebClient;
import com.dreambank.card.decision.service.client.ExperianApiWebClient;
import com.dreambank.card.decision.service.client.TransunionApiWebClient;
import com.dreambank.card.decision.service.controller.DecisionController;
import com.dreambank.card.decision.service.model.CreditScoreRequest;
import com.dreambank.card.decision.service.model.DecisionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientScoreApiDelegate.class)
class ClientScoreApiDelegateTest {

    @Autowired
    private ClientScoreApiDelegate delegate;

    @MockBean
    private EquifaxApiWebClient equifaxApiWebClient;

    @MockBean
    private ExperianApiWebClient experianApiWebClient;

    @MockBean
    private TransunionApiWebClient transunionApiWebClient;

    @MockBean
    private CardDecisionHelper cardDecisionHelper;

    @Test
    void getDecisionResponse() {
        CreditScoreRequest request = CreditScoreRequest.builder()
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .ssn("345-47-1145").build();
        Mockito.when(equifaxApiWebClient.inquireCreditScory(any(CreditScoreRequest.class))).thenReturn(Mono.just(650));
        Mockito.when(experianApiWebClient.inquireCreditScory(any(CreditScoreRequest.class))).thenReturn(Mono.just(651));
        Mockito.when(transunionApiWebClient.inquireCreditScory(any(CreditScoreRequest.class))).thenReturn(Mono.just(652));
        Mockito.when(cardDecisionHelper.getDecision(650, 651,652)).thenReturn("APPROVED");
        DecisionResponse decisionResponse = DecisionResponse.builder().status("APPROVED").equifaxCreditScore(650).experianCreditScore(651).transUnionCreditScore(652).build();

        Mono<DecisionResponse> responseMono = delegate.getDecisionResponse(request);
        StepVerifier.create(responseMono).expectNextCount(1)
                .expectComplete()
                .verify();


    }
}