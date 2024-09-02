package com.dreambank.card.decision.service.client;

import com.dreambank.card.decision.service.model.CreditScoreRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EquifaxApiWebClientTest {

    @Test
    void inquireCreditScory_success() {

        ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
        WebClient webClient = WebClient.builder().exchangeFunction(exchangeFunction).build();
        EquifaxApiWebClient equifaxApiWebClient= new EquifaxApiWebClient(webClient);
        CreditScoreRequest request = CreditScoreRequest.builder()
                .firstName("John")
                .lastName("Wright")
                .ssn("345-47-1145").build();
        String json = "{\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Wright\",\n" +
                "    \"ssn\": \"345-47-1145\",\n" +
                "    \"creditScore\": 560\n" +
                "}";
        ClientResponse response = ClientResponse.create(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(json).build();

        when(exchangeFunction.exchange(any(ClientRequest.class))).thenReturn(Mono.just(response));


        Mono<Integer> responseMono = equifaxApiWebClient.inquireCreditScory(request);
        StepVerifier
                .create(responseMono)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }


    @Test
    void inquireCreditScory_failure() throws URISyntaxException {

        ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
        WebClient webClient = WebClient.builder().exchangeFunction(exchangeFunction).build();
        EquifaxApiWebClient equifaxApiWebClient= new EquifaxApiWebClient(webClient);
        CreditScoreRequest request = CreditScoreRequest.builder()
                .firstName("John")
                .lastName("Wright")
                .ssn("345-47-1145").build();
        String json = "{\n" +
                "    \"firstName\": \"John\",\n" +
                "    \"lastName\": \"Wright\",\n" +
                "    \"ssn\": \"345-47-1145\",\n" +
                "    \"creditScore\": 560\n" +
                "}";
        ConnectException connEx = new ConnectException("Simulated connection exception");
        when(exchangeFunction.exchange(any(ClientRequest.class))).thenThrow(new WebClientRequestException(connEx, HttpMethod.POST, new URI("/credit-score"), new HttpHeaders()));



        Mono<Integer> responseMono = equifaxApiWebClient.inquireCreditScory(request);
        StepVerifier
                .create(responseMono)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

}